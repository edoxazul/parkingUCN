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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

public class DatabaseUpload {

  //The Logger
  public static Logger logger = LoggerFactory.getLogger(Scrapper.class);

  //For DB read
  private Dao<Academic, Integer> academicDao;

  //The DB connection
  ConnectionSource connectionSource;

  //The url database path
  private String urlDB = "";

  private static DatabaseUpload databaseUpload;

  private DatabaseUpload(){

    // Set the databse path
    urlDB = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\academics.db";

    logger.debug("Retreaving database from path {}", urlDB);

  }

  /**
   * Return the instance.
   * @return databaseUpload
   */
  public static DatabaseUpload getInstance() throws IOException {
    if (databaseUpload == null) {
      databaseUpload = new DatabaseUpload();
    }
    return databaseUpload;
  }



  public void uploadDatabase() throws SQLException {

    List<Academic> row = academicDao.queryForAll();


  }

  private void readDatabase(){

    connectionSource = null;

    try {


      // FIXME: No se consigue la conexion con la BD
      //defines a database and the DAO
      connectionSource = new JdbcConnectionSource(urlDB);
      //Read only connection
      connectionSource.getReadOnlyConnection("Academicos");
      this.academicDao = DaoManager.createDao(connectionSource, Academic.class);

      logger.debug("Database connection and DAO created");

    } catch (SQLException e) {
      logger.error("Error creating a DB connection: " + e.getMessage());
    }
  }

}
