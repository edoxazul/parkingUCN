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
["cs:namespace:ParkingUcn.ZeroIce","java:package:cl.ucn.disc.pdis.parkingucn.zeroice"]
module model {


    /**
    * Categoria de la persona
    */
    enum CategoriaPersona {
        FUNCIONARIO,ACADEMICO,ESTUDIANTE
    }

    /**
    * Sexo de la persona
    */
    enum Sexo {
        VAR,MUJ,OTHER
    }


    /**
     * Class Persona
     */
    ["cs:property"]
    class Persona{

        /**
         * Primary Key
         */
        int uid;

        /**
         * Run: 189725965
         */
        string run;

        /**
         * Nombre
         */
        string nombre;

        /**
         * Sexo de la persona
         */
        Sexo sexo;

        /**
         * Unidad perteneciente.
         */
        string unidad;

        /**
         * email
         */
        string email;

        /**
         * Telefono movil
         */
        string telefonoMovil;

        /**
         * Telefono fijo
         */
        string telefonoFijo;

        /**
        * Categoria de la persona
        */
        CategoriaPersona categoriaPersona;


    }

    /**
     * Class vehiculo
     */
    ["cs:property"]
    class Vehiculo{

        /**
         * Primary Key
         */
        int uid;

        /**
         * Patente del vehiculo
         */
        string patente;

        /**
         * Marca vehiculo
         */
        string marca;  

        /**
         * Modelo del vehiculo
         */
        string modelo;

        /**
         * Año del vehiculo
         */
        string anio;

        /**
         * Obervaciones
         */
        string observaciones;

        /**
         * Duenio del vehiculo.
         */
        string runDuenio;
        
    }
    
    exception NotFoundException
    {
        string reason = "information not found on the system's database";
    }
    exception DuplicateDataException
    {
        string reason = "information alredy exist on the system's database";
    }

    /**
    * Interfaz de Contratos
    */
    interface Contratos {

        /**
         * Verificacion de la identidad de una persona.
         * @param run identificador unico de la persona.
         * @return Persona confirmada la autenticidad, se retorna los datos de la persona.
         * @throws NotFoundException si la informacion ingresada no existe.
         */
        Persona verificarPersona(string run);
            // throws NotFoundException;

        /**
         * Autoriza la entrada o salida del vehiculo.
         * @param patente identificador unico del vehiculo.
         * @param tipo el vehiculo puede entrar(true) o salir(false).
         * @return Vehiculo confirmada la entrada/salida en el sistema, se retornan los datos del vehiculo.
         * @throws NotFoundException si la informacion ingresada no existe.
         */
        Vehiculo autorizarVehiculo(string patente, bool tipo);
            // throws NotFoundException;

        /**
         * Registrar una nueva persona en el sistema.
         * @param Persona datos de la persona.
         * @return Persona se retornan los datos ingresados en el sistema como confirmacion.
         * @throws DuplicateDataException la informacion ingresada ya existe en el sistema.
         */
        Persona registrarPersona(Persona persona)
            throws DuplicateDataException;

        /**
         * Eliminar una persona del sistema.
         * @param run identificador unico de la persona.
         * @return Persona se retornan los datos eliminados en el sistema como confirmacion.
         * @throws NotFoundException si la informacion ingresada no existe.
         */
        Persona eliminarPersona(string run);
            // throws NotFoundException;

        /**
         * Editar una persona del sistema.
         * @param Persona datos actualiados de la persona.
         * @return Persona se retornan los datos cambiados en el sistema como confirmacion.
         */
        Persona editarPersona(Persona persona);

        /**
         * Registrar un nuevo vehiculo en el sistema
         * @param Vehiculo datos del vehiculo
         * @return Vehiculo se retornan los datos ingresados en el sistema como confirmacion.
         * @throws DuplicateDataException la informacion ingresada ya existe en el sistema.
         */
        Vehiculo registrarVehiculo(Vehiculo vehiculo);
            // throws DuplicateDataException;

        /**
         * Eliminar un vehiculo del sistema.
         * @param patente identificador unico del vehiculo.
         * @return Vehiculo se retornan los datos eliminados en el sistema como confirmacion.
         * @throws NotFoundException si la informacion ingresada no existe.
         */
        Vehiculo eliminarVehiculo(string patente);
            // throws NotFoundException;

        /**
         * Editar un vehiculo del sistema.
         * @param Vehiculo datos actualiados del vehiculo.
         * @return Vehiculo se retornan los datos cambiados en el sistema como confirmacion.
         */
        Vehiculo editarVehiculo(Vehiculo vehiculo);

        void populateDatabase(Persona persona);


    }



}
