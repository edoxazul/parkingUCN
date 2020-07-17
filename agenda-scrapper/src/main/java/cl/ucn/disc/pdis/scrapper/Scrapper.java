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

import cl.ucn.disc.pdis.scrapper.bd.Academic;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Implementation of ScrapperCSV.
 *
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
public class Scrapper {

  //The Logger
  public static Logger logger = LoggerFactory.getLogger(Scrapper.class);

  //Url for principal scrapping
  private String UrlDirectorio = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";
  private String UrlRutificador = "https://www.nombrerutyfirma.com/buscar";
  //id's from academics
  private int ini = 21;
  private int end = 29730; //this is the last
  private Random random = new Random(); //for delay

  //For Singleton
  private static Scrapper scrapper;

  //For DB
  private Dao<Academic, Integer> academicDao;
  ConnectionSource connectionSource;


  /**
   * Void Constructor for Singleton Pattern.
   * @throws IOException e
   */
  private Scrapper() throws IOException {
    //nothing to do
  }

  /**
   * Return the instance.
   * @return scrapper
   */
  public static Scrapper getInstance() throws IOException {
    if (scrapper == null) {
      scrapper = new Scrapper();
    }
    return scrapper;
  }

  /**
   * Scrapper from directorioUCN and get all Persons.
   * @throws IOException e
   */
  public void ScrapperDirectorio() throws IOException {
    //create a DB
    initializeDB();

    //range for scrapper in directorio ucn
    int ini = 21;
    int end = 29730;

    // for fixed phone number
    String phoneNumber;

    for (int i = ini; i <= end; i++) {
      Document doc = Jsoup.connect(this.UrlDirectorio + i).get();
      //get data
      String nombre = doc.getElementById("lblNombre").text();
      String cargo = doc.getElementById("lblCargo").text();
      String unidad = doc.getElementById("lblUnidad").text();
      String email = doc.getElementById("lblEmail").text();
      String telefono = doc.getElementById("lblTelefono").text();
      String oficina = doc.getElementById("lblOficina").text();
      String direccion = doc.getElementById("lblDireccion").text();

      if (nombre.isEmpty()) {
        continue;
      }
      logger.debug("Code: " + i + " - Name: " + nombre);
      //Format the phone number of academic
      phoneNumber = telefono.replace("Fono ", "");

      String rut = "";

      try {
        //try to get rut from academic and save this academic
        rut = ScrapperRutificador(nombre);
        Academic academic = new Academic(i, rut, nombre, cargo,
                                        unidad, email, phoneNumber,
                                        oficina, direccion);
        this.academicDao.create(academic);

      } catch (SQLException e) {

        logger.error(e.getMessage());
      }
    }
    connectionSource.close();
  }

  /**
   * Scrapper with name of academic and get RUT from rutificador.
   * @param name from scraperDirectorio
   * @return rut of Academic
   * @throws IOException e
   */
  public String ScrapperRutificador(String name) throws IOException {
    // Make the connection
    Connection.Response response =
        Jsoup.connect(this.UrlRutificador)
            .method(Connection.Method.POST)
            // Send the name
            .data("term", name)
            .execute();

    // Return the html document
    Document doc = response.parse();

    // Select all tables on the html document
    Elements tables = doc.select("tr");

    // If there is only 1 table, that mean that there is no result for that name
    if (tables.size() == 1) {

      this.logger.error("No match found for: {}", name);
      return "null";

    } else {

      // Extract the run from the table
      String run = tables.get(1).getAllElements().get(2).text();

      // Take out the simbols
      run = run
          .replace(".", "")
          .replace("-", "");
      return run;
    }
  }

  /**
   * inicialize the database SQLITE.
   */
  public void initializeDB() {
    String urlDB = "jdbc:sqlite:academics.db";
    connectionSource = null;
    try {

      //defines a database and the DAO
      connectionSource = new JdbcConnectionSource(urlDB);
      this.academicDao = DaoManager.createDao(connectionSource, Academic.class);
      TableUtils.createTableIfNotExists(connectionSource, Academic.class);
      logger.debug("Database and DAO created");

    } catch (SQLException e) {
      logger.error("Error creating a DB connection: " + e.getMessage());
    }
  }
}
