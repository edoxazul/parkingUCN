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
         * email
         */
        string email;

        /**
         * Telefono movil
         */
        string telefonoMovil;

        /**
        * Categoria de la persona
        */
        CategoriaPersona categoria;

        
    }

    /**
       * Categoria de la persona
       */
     enum CategoriaPersona {
        FUNCIONARIO, ACADEMICO, ESTUDIANTE
     };

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
         * Patente
         */
        string patente;

        /**
         * Marca coche
         */
        string marca;  

        /**
         * Modelo del coche
         */
        string modelo;

        /**
         * Año del coche
         */
        string anio;

        /**
         * Obervaciones
         */
        string observaciones;

        /**
         * Duenio del coche
         */
        string runDuenio;
        
    }

    /**
    * Interfaz de Contratos
    */
    interface Contratos {

        /**
         * Verificacion de la identidad de una persona.
         * @param run identificador unico de la persona.
         * @return Persona confirmada la autenticidad, se retorna los datos de la persona.
         */
        Persona verificarPersona(string run)

        /**
         * Autoriza la entrada o salida del vehiculo.
         * @param patente identificador unico del vehiculo.
         * @param tipo el vehiculo puede entrar(true) o salir(false).
         * @return Vehiculo confirmada la entrada/salida en el sistema, se retornan los datos del vehiculo.
         */
        Vehiculo autorizarVehiculo(string patente, bool tipo)

        /**
         * Registrar una nueva persona en el sistema.
         * @param Persona datos de la persona.
         * @return Persona se retornan los datos ingresados en el sistema como confirmacion.
         */
        Persona registrarPersona(Persona persona)

        /**
         * Eliminar una persona del sistema.
         * @param run identificador unico de la persona.
         * @return Persona se retornan los datos eliminados en el sistema como confirmacion.
         */
        Persona eliminarPersona(string run)

        /**
         * Editar una persona del sistema.
         * @param Persona datos actualiados de la persona.
         * @return Persona se retornan los datos cambiados en el sistema como confirmacion.
         */
        Persona editarPersona(Persona persona)

        /**
         * Registrar un nuevo vehiculo en el sistema
         * @param Vehiculo datps del vehiculo
         * @return Vehiculo se retornan los datos ingresados en el sistema como confirmacion.
         */
        Vehiculo registrarVehiculo(Vehiculo vehiculo)



    }

}
