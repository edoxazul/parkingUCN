package cl.ucn.disc.pdis.scrapper;

import java.io.IOException;

public class Main {

  /**
   * Main.
   * @param args arguments
   * @throws IOException e
   */
  public static void main(String[] args) throws IOException, InterruptedException {

   Scrapper scrapper = Scrapper.getInstance();
   scrapper.ScrapperDirectorio();
  }
}
