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


// https://doc.zeroc.com/ice/3.7/language-mappings/java-mapping/client-side-slice-to-java-mapping/customizing-the-java-mapping
["cs:namespace:ParkingUcn.ZeroIce"]
module model {

    /**
     * Class Person
     */
    ["cs:property"]
    class Person{

        /**
         * Primary Key
         */
        int uid;

        /**
         * Rut: 189725965
         */
        string rut;

        /**
         * name
         */
        string name;

        /**
        * The position
        */
        string position;

        /**
         * unit
         */
        string unit;

        /**
         * email
         */
        string email;

        /**
         * phone number
         */
        string phone;

        /**
         * office
         */
        string office;

        /**
         * address
         */
        string address;
        
    }

    /**
     * Class Vehicle
     */
    ["cs:property"]
    class Vehicle{

        /**
         * Primary Key
         */
        int uid;

        /**
         * patent
         */
        string patent;

        /**
         * car brand
         */
        string brand;  

        /**
         * model of car
         */
        string model;

        /**
         * year of car
         */
        string year;

        /**
         * observation
         */
        string observation;

        /**
         * rut car owner
         */
        string rutOwner;
        
    }

    /**
     * The base system.
     */
     interface TheSystem {

        /**
         * @return the diference in time between client and server.
         */
        long getDelay(long clientTime);

     }

}
