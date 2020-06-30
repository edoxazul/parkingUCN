/*
 * Copyright (c) 2020. This is only for education, do not use in production. GG
 *
 * Alvaro Lucas Castillo Calabacero
 * alvarolucascc96@gmail.com
 */

package cl.ucn.disc.pdis.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.FileWriter;

import java.util.Random;

import java.io.IOException;

public class ScrapperCsv {
    /**
     * Main
     *
     * @param args args
     * @throws IOException e
     */
    public static void main(String[] args) throws IOException {

        /**
         * Url for principal scrapping
         */
        String URL ="http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";

        /**
        id's from academics
         */
        int ini = 21;
        int end = 29730; //this is the last
        Random random = new Random(); //for delay

        /**
         * scrapping variables
         */
        Document doc;
        Element lblNombre;
        Element lblCargo;
        Element lblUnidad;
        Element lblEmail;
        Element lblTelefono;
        Element lblOficina;
        Element lblDireccion;

        /**
         * csv file variable
         */
        FileWriter fileWriter = new FileWriter("./src/main/resources/academics.csv");
        //for index
        //fileWriter.append("Nombre,Cargo,Unidad,Email,Telefono,Oficina,Direccion");

        /**
         * for fixed number
         */
        String phoneNumber;

        for (int i=ini ; i <=end;i++) {
            doc = Jsoup.connect(URL+i).get();
            //get data
            lblNombre =  doc.getElementById("lblNombre");
            lblCargo =  doc.getElementById("lblCargo");
            lblUnidad =  doc.getElementById("lblUnidad");
            lblEmail =  doc.getElementById("lblEmail");
            lblTelefono =  doc.getElementById("lblTelefono");
            lblOficina =  doc.getElementById("lblOficina");
            lblDireccion =  doc.getElementById("lblDireccion");


            /**
             * ignore this element if the name is void.
             */
            if (lblNombre.text().isEmpty()) {
                continue;
            }
            /**
             * Format the phone number of academic
             */
            phoneNumber = lblTelefono.text().replace("Fono ","");

            /**
             * Save Data and use a delay
             */
            try {
                fileWriter.append(String.valueOf(i)+","); //the index in URL
                fileWriter.append(lblNombre.text()+",");
                fileWriter.append(lblCargo.text()+",");
                fileWriter.append(lblUnidad.text()+",");
                fileWriter.append(lblEmail.text()+",");
                fileWriter.append(phoneNumber+","); //fixed number
                fileWriter.append(lblOficina.text()+",");
                fileWriter.append(lblDireccion.text());
                fileWriter.append("\n");

                /**
                 * Delay for the security
                 */
                Thread.sleep(1000+random.nextInt(1000));

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
