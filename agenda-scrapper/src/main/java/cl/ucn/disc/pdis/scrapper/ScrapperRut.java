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

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.nodes.Document;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScrapperRut {


    /*
    The logger
     */
    public static Logger logger = LoggerFactory.getLogger(ScrapperRut.class);

    public static void main(String[] args) throws IOException {

        // The URL
        String URL = "https://www.nombrerutyfirma.com/buscar";

        try{
            Connection.Response response =
                    Jsoup.connect(URL)
                            .method(Connection.Method.POST)
                            .data("term", "Ignacio Fuenzalida Veas")
                            .execute();

            Document doc = response.parse();

            Elements tables = doc.select("tr");


            if(tables.size() == 1){
                logger.error("No match found.");
                return;
            }

            // Extract the run from the table
            String run = tables.get(1).getAllElements().get(2).text();


        } catch (ConnectException e){
            e.printStackTrace();
            return;
        }

        CSVReader reader = new CSVReader(new FileReader("./src/main/resources/academics.csv"));
        CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/academics2.csv"), ',');

        /*
        String[] entries = null;
        while ((entries = reader.readNext()) != null) {
            ArrayList list = new ArrayList(Arrays.asList(entries));
            list.add(xxx)); // Add the new element here
            writer.writeNext(list);
        }
        */


        String [] entries;
        while ((entries = reader.readNext()) != null) {

            for(i = 0 ;)

            ArrayList list = new ArrayList(entries);

            String[] arr = Arrays.copyOf(list, list.length + 1);
            list.add("test");
            writer.writeNext(list);


            System.out.println(entries[0] + entries[1] + "etc...");
        }

        writer.close();






    }


}
