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
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sqlite Implementation.
 * @author Ignacio Fuenzalida
 * @author Alvaro Castillo
 * @author Eduardo Alvarez
 */
public class Adapter {

  //The Logger
  public static Logger logger = LoggerFactory.getLogger(Adapter.class);
  private Dao<Academic, Integer> academicDao;
  private CSVReader reader;

  // For Singleton Pattern
  private static Adapter adapter;

  /**
   * Void constructor for Singleton.
   * @throws IOException e
   */
  private Adapter(){
    // nothing to do
  }

  /**
   * Get Instance for Singleton.
   * @return Instance for Adapter
   * @throws IOException e
   */
  public static Adapter getInstance(){
    if (adapter == null) {
      adapter = new Adapter();
    }
    return adapter;
  }

  /**
   * method that reads the csv and stores each record within the database
   * @throws IOException
   */
  public void storeData() throws IOException {

    String urlDB = "jdbc:sqlite:academics.db";
    ConnectionSource connectionSource = null;

    try {

      //defines a database and the DAO
      connectionSource = new JdbcConnectionSource(urlDB);
      this.academicDao = DaoManager.createDao(connectionSource, Academic.class);
      TableUtils.createTableIfNotExists(connectionSource, Academic.class);
      this.reader = new CSVReader(new FileReader("./src/main/resources/academics2.csv"));
      logger.debug("Database and DAO created");

    } catch (SQLException e) {
      logger.error("Error creating a DB connection: " + e.getMessage());
    } catch (IOException e) {
      logger.error("Error with CSV: "+ e.getMessage());
    }

    //read csv and get records
    String[] entries;
    while ((entries = this.reader.readNext()) != null) {

      //TODO: Migrate from CSV to BD

    }
    connectionSource.close();
  }

}
