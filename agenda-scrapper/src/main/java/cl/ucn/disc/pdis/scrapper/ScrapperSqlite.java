/*
 * Copyright (c) 2020. This is only for education, do not use in production. GG
 *
 * Alvaro Lucas Castillo Calabacero
 * alvarolucascc96@gmail.com
 */

package cl.ucn.disc.pdis.scrapper;


import checkers.units.quals.A;
import cl.ucn.disc.pdis.scrapper.BD.Academic;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ScrapperSqlite {

    /**
     * The Logger
     */
    public static Logger logger = LoggerFactory.getLogger(ScrapperSqlite.class);


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        /**
         * Data of sqlite
         */
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
     * get academic with scrapping
     *
     * @param id
     * @return
     */
    private static Academic scrapping(int id) {
        try {
            final String URL = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";
            Document doc = Jsoup.connect(URL + id).get();
            String name = doc.getElementById("lblNombre").text();
            String position = doc.getElementById("lblCargo").text();
            String unit = doc.getElementById("lblUnidad").text();
            String email = doc.getElementById("lblEmail").text();
            String phone = doc.getElementById("lblTelefono").text();
            String office = doc.getElementById("lblOficina").text();
            String address = doc.getElementById("lblDireccion").text();

            /**
             * Micro-optimization
             */
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
