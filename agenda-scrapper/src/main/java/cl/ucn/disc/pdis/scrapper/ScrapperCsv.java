/*
 * Copyright (c) 2020. This is only for education, do not use in production. GG
 *
 * Alvaro Lucas Castillo Calabacero
 * alvarolucascc96@gmail.com
 */

package cl.ucn.disc.pdis.scrapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ScrapperCsv {
  /**
   * Main of ScrapperCSV.
   * @param args args
   * @throws IOException e
   */
  public static void main(String[] args) throws IOException {


    //Url for principal scrapping
    String theUrl = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";

    //id's from academics
    int ini = 21;
    int end = 29730; //this is the last
    Random random = new Random(); //for delay

    // csv file variable
    FileWriter fileWriter = new FileWriter("./src/main/resources/academics.csv");

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
