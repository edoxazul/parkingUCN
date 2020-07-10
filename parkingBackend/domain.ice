
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

}
