/*
 * MIT License
 *
 * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.ucn.disc.pdis.scrapper;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of Scrapper for Rut.
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
public class ScrapperRut {

  //The logger
  public static Logger logger = LoggerFactory.getLogger(ScrapperRut.class);

  // The URL
  String url = "https://www.nombrerutyfirma.com/buscar";

  // Open the csv without the run
  CSVReader reader = new CSVReader(new FileReader("./src/main/resources/academics.csv"));

  // New csv to append the run
  CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/academics2.csv"), ',');

  // For delay
  Random random = new Random();

  // For Singleton Pattern
  private static ScrapperRut scrapperRut;

  /**
   * Void constructor for Singleton.
   * @throws IOException e
   */
  private ScrapperRut() throws IOException {
    //Nothing to do
  }

  /**
   * Get Instance for Singleton.
   * @return Instance for ScrapperRut
   * @throws IOException e
   */
  public static ScrapperRut getInstance() throws IOException {
    if (scrapperRut == null) {
      scrapperRut = new ScrapperRut();
    }
    return scrapperRut;
  }

  /**
   * Main of ScrapperRut.
   * @throws IOException          IOException
   * @throws InterruptedException InterruptedException
   */
  public void scrapperToRut() throws IOException, InterruptedException {

    String[] entries;
    while ((entries = this.reader.readNext()) != null) {

      try {
        // Make the connection
        Connection.Response response =
            Jsoup.connect(this.url)
                .method(Connection.Method.POST)
                // Send the name
                .data("term", entries[1])
                .execute();

        // Return the html document
        Document doc = response.parse();

        // Select all tables on the html document
        Elements tables = doc.select("tr");

        String run;

        // If there is only 1 table, that mean that there is no result for that name
        if (tables.size() == 1) {

          this.logger.error("No match found for: {}", entries[1]);
          run = "null";

        } else {

          // Extract the run from the table
          run = tables.get(1).getAllElements().get(2).text();

          // Take out the simbols
          run = run
              .replace(".", "")
              .replace("-", "");
        }

        // Append the run to the list
        entries = Arrays.copyOf(entries, entries.length + 1);
        entries[entries.length - 1] = run;

        // Write the line with the run
        writer.writeNext(entries);

      } catch (Exception e) {
        e.printStackTrace();
        return;
      }

      // The delay
      Thread.sleep(100 + this.random.nextInt(50));

    }

    this.writer.close();

  }

}
