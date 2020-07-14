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

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of ScrapperCSV.
 *
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
public class ScrapperCsv {

  //The Logger
  public static Logger logger = LoggerFactory.getLogger(ScrapperCsv.class);

  //Url for principal scrapping
  String theUrl = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";

  //id's from academics
  int ini = 21;
  int end = 29730; //this is the last
  Random random = new Random(); //for delay

  // csv file variable
  FileWriter fileWriter = new FileWriter("./src/main/resources/academics.csv");

  //For Singleton
  private static ScrapperCsv scrapperCsv;

  /**
   * Void Constructor for Singleton Pattern.
   * @throws IOException e
   */
  private ScrapperCsv() throws IOException {
    //nothing to do
  }

  /**
   * Return the instance.
   * @return scrapperCSV
   */
  public static ScrapperCsv getInstance() throws IOException {
    if (scrapperCsv == null) {
      scrapperCsv = new ScrapperCsv();
    }
    return scrapperCsv;
  }

  /**
   * Scrapping and get csv.
   * @throws IOException e
   */
  public void scrapperToCsv() throws IOException {

    // for fixed phone number
    String phoneNumber;

    for (int i = ini; i <= end; i++) {
      Document doc = Jsoup.connect(theUrl + i).get();
      //get data
      Element lblNombre = doc.getElementById("lblNombre");
      Element lblCargo = doc.getElementById("lblCargo");
      Element lblUnidad = doc.getElementById("lblUnidad");
      Element lblEmail = doc.getElementById("lblEmail");
      Element lblTelefono = doc.getElementById("lblTelefono");
      Element lblOficina = doc.getElementById("lblOficina");
      Element lblDireccion = doc.getElementById("lblDireccion");

      logger.debug("Code: "+i+" - Name: "+lblNombre.text());

      //ignore this element if the name is void
      if (lblNombre.text().isEmpty()) {
        continue;
      }

      //Format the phone number of academic
      phoneNumber = lblTelefono.text().replace("Fono ", "");

      //Save Data and use a delay
      try {
        fileWriter.append(String.valueOf(i) + ","); //the index in URL
        fileWriter.append(lblNombre.text() + ",");
        fileWriter.append(lblCargo.text() + ",");
        fileWriter.append(lblUnidad.text() + ",");
        fileWriter.append(lblEmail.text() + ",");
        fileWriter.append(phoneNumber + ","); //fixed number
        fileWriter.append(lblOficina.text() + ",");
        fileWriter.append(lblDireccion.text());
        fileWriter.append("\n");

        //Delay for security
        Thread.sleep(1000 + random.nextInt(1000));

      } catch (InterruptedException e) {
        e.printStackTrace();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    fileWriter.flush();
    fileWriter.close();
  }

}
