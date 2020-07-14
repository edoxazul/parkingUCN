package cl.ucn.disc.pdis.scrapper;

import java.io.IOException;

public class Main {

  /**
   * Main.
   * @param args arguments
   * @throws IOException e
   */
  public static void main(String[] args) throws IOException {
    //Get data from "ucn directory"
    ScrapperCsv scrapperCsv = ScrapperCsv.getInstance();
    scrapperCsv.scrapperToCsv();

    //Update data from "ucn directory" and add RUT
    ScrapperRut scrapperRut = ScrapperRut.getInstance();
    
  }
}
