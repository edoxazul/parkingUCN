package cl.ucn.disc.pdis.scrapper;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

  /**
   * Main.
   * @param args arguments
   * @throws IOException e
   */
  public static void main(String[] args) throws IOException, InterruptedException, SQLException {


    //Scrapper scrapper = Scrapper.getInstance();
    //scrapper.ScrapperDirectorio();

    DatabaseUpload databaseUpload = DatabaseUpload.getInstance();
    databaseUpload.uploadDatabase();

  }
}
