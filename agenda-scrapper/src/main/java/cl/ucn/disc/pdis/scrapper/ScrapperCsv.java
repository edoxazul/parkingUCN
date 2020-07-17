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
  private String theUrl = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";

  //id's from academics
  private int ini = 21;
  private int end = 29730; //this is the last
  private Random random = new Random(); //for delay

  // csv file variable
  private FileWriter fileWriter = new FileWriter("./src/main/resources/academics.csv");

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
      String lblNombre = doc.getElementById("lblNombre").text();
      String lblCargo = doc.getElementById("lblCargo").text();
      String lblUnidad = doc.getElementById("lblUnidad").text();
      String lblEmail = doc.getElementById("lblEmail").text();
      String lblTelefono = doc.getElementById("lblTelefono").text();
      String lblOficina = doc.getElementById("lblOficina").text();
      String lblDireccion = doc.getElementById("lblDireccion").text();

      logger.debug("Code: "+i+" - Name: "+lblNombre);

      //ignore this element if the name is void
      if (lblNombre.isEmpty()) {
        continue;
      }

      //Format the phone number of academic
      phoneNumber = lblTelefono.replace("Fono ", "");

      //format to avoid mistakes
      if (lblOficina.contains(",")) {
        lblOficina.replace(","," ");
      }

      if (lblDireccion.contains(",")) {
        lblDireccion.replace(","," ");
      }

      //Save Data and use a delay
      try {
        fileWriter.append(String.valueOf(i) + ","); //the index in URL
        fileWriter.append(lblNombre + ",");
        fileWriter.append(lblCargo + ",");
        fileWriter.append(lblUnidad+ ",");
        fileWriter.append(lblEmail+ ",");
        fileWriter.append(phoneNumber + ","); //fixed number
        fileWriter.append(lblOficina+ ",");
        fileWriter.append(lblDireccion);
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
