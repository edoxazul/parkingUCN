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

package cl.ucn.disc.pdis.scrapper.BD;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sqlite Implementation.
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
public class ScrapperSqlite {

  //The Logger
  public static Logger logger = LoggerFactory.getLogger(ScrapperSqlite.class);


  /**
   * Main.
   *
   * @param args arguments
   */
  public static void main(String[] args) throws InterruptedException, IOException {

    //Data of sqlite
    String urlDB = "jdbc:sqlite:academics.db";
    ConnectionSource connectionSource = null;
    Dao<Academic, Integer> academicDao = null;

    try {
      //Create a database and dao
      connectionSource = new JdbcConnectionSource(urlDB);
      academicDao = DaoManager.createDao(connectionSource, Academic.class);
      TableUtils.createTableIfNotExists(connectionSource, Academic.class);
      logger.debug("Database and DAO created");

    } catch (SQLException e) {
      logger.error("Error creating a DB connection: " + e.getMessage());

    }

    int ini = 0;
    int end = 29730;

    try {
      String idDB = academicDao.queryRaw("select max(id) from academics").getFirstResult()[0];
      ini = Integer.parseInt(idDB);
    } catch (SQLException throwables) {
      logger.error(throwables.getMessage());
    }
    //Scrapping all academics
    for (; ini <= end; ini++) {
      logger.info("Scrapping Academic with id: " + ini);

      Academic academic = scrapping(ini);
      if (academic == null) {
        continue;
      }

      try {
        academicDao.create(academic);
      } catch (SQLException throwables) {
        logger.error("Error inserting academic: " + ini);
      }

      Random random = new Random();
      Thread.sleep(1000 + random.nextInt(1000));
    }
    assert connectionSource != null;
    connectionSource.close();
  }

  /**
   * Get academic with scrapping.
   * @param id for scrapping
   * @return Academic a new academic
   */
  private static Academic scrapping(int id) {
    try {
      final String Url = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";
      Document doc = Jsoup.connect(Url + id).get();
      String name = doc.getElementById("lblNombre").text();
      String position = doc.getElementById("lblCargo").text();
      String unit = doc.getElementById("lblUnidad").text();
      String email = doc.getElementById("lblEmail").text();
      String phone = doc.getElementById("lblTelefono").text();
      String office = doc.getElementById("lblOficina").text();
      String address = doc.getElementById("lblDireccion").text();

      //Micro optimization
      if (!name.isEmpty()) {
        Academic academic = new Academic(id, name, position, unit, email, phone, office, address);
        logger.debug("Academic: " + academic.toString());
        return academic;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
