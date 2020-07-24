/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package cl.ucn.disc.pdis.parkingapp;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ucn.disc.pdis.parkingucn.zeroice.model.CategoriaPersona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.DuplicateDataException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotFoundException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Persona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.RunRelationNotFoundException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Sexo;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;

import static org.junit.Assert.*;

public class ContratosTest {

    //The Logger
    public static Logger logger = LoggerFactory.getLogger(ContratosTest.class);

    @Test
    public void registrarPersona() throws DuplicateDataException {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Persona persona1 = new Persona();

        persona1.run = "11111111";
        persona1.nombre = "Alvaro Castillo";
        persona1.sexo = Sexo.OTHER;
        persona1.email = "alvaro.castillo@alumnos.ucn.cl";
        persona1.categoriaPersona = CategoriaPersona.ESTUDIANTE;

        logger.debug("Persona to backend: {} {} {}", persona1.nombre, persona1.run, persona1.sexo);
        Persona persona2 = contratos.registrarPersona(persona1);
        logger.debug("Persona from backend: {} {} {}", persona2.nombre, persona2.run, persona2.sexo);

        assertNotNull(persona2);
        logger.debug("DONE: Persona added succefully on database!");

        // Send the same person, should return Duplicate
        assertThrows(DuplicateDataException.class, () -> contratos.registrarPersona(persona1));
        logger.debug("DONE: Exception reach because duplicate data!");

    }

    @Test
    public void registrarVehiculo() throws DuplicateDataException, RunRelationNotFoundException {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Vehiculo vehiculo1 = new Vehiculo();

        vehiculo1.patente = "XD-6996";
        vehiculo1.marca = "Mazda";
        vehiculo1.modelo = "NZT";
        vehiculo1.anio = 3000;
        vehiculo1.observaciones = "Sin capo";
        vehiculo1.runDuenio = "193982336";

        logger.debug("Vehiculo to backend: {} {} {}",
                vehiculo1.patente,
                vehiculo1.marca,
                vehiculo1.runDuenio);

        Vehiculo vehiculo2 = contratos.registrarVehiculo(vehiculo1);

        logger.debug("Vehiculo from backend: {} {} {}",
                vehiculo2.patente,
                vehiculo2.marca,
                vehiculo2.runDuenio);

        assertNotNull(vehiculo1);
        logger.debug("DONE: Vehicle added succefully on database!");

        // Send the same vehicle, should return Duplicate
        assertThrows(DuplicateDataException.class,
                () -> contratos.registrarVehiculo(vehiculo1));
        logger.debug("DONE: Exception reach because duplicate data!");

        Vehiculo vehiculo3 = new Vehiculo();

        vehiculo3.patente = "AA-6996";
        vehiculo3.marca = "TOYOTA";
        vehiculo3.modelo = "NZT";
        vehiculo3.anio = 4000;
        vehiculo3.observaciones = "Sin capo";
        vehiculo3.runDuenio = "1234552";

        assertThrows(RunRelationNotFoundException.class,
                () -> contratos.registrarVehiculo(vehiculo3));
        logger.debug("DONE: Exception reach because run not found on persona table!");

    }

    @Test
    public void eliminarPersona() {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Persona persona = contratos.eliminarPersona("11111111");
        logger.debug("Persona from backend: {} {} {}", persona.nombre, persona.run, persona.sexo);

        assertNotNull(persona);
        logger.debug("DONE: Persona added succefully on database!");

        // Send the same person, should return null
        Persona persona2 = contratos.eliminarPersona("11111111");
        assertNull(persona2);
        logger.debug("DONE: Exception reach because null data!");

    }
    @Test
    public void eliminarVehiculo() {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Vehiculo vehiculo = contratos.eliminarVehiculo("CHLJ90");
        logger.debug("Vehiculo from backend: {} {} {}", vehiculo.patente, vehiculo.modelo, vehiculo.marca);

        assertNotNull(vehiculo);
        logger.debug("DONE: Vehiculo deleted succefully on database!");

        // Send the same vehiculo, should return null
        Vehiculo vehiculo1 = contratos.eliminarVehiculo("CHLJ90");
        assertNull(vehiculo1);
        logger.debug("DONE: Exception reach because null data!");
    }

    @Test
    public void editarVehiculo(){

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.uid = 2;
        vehiculo1.patente = "XD-6996";
        vehiculo1.marca = "Mazda";
        vehiculo1.modelo = "NZT";
        vehiculo1.anio = 3000;
        vehiculo1.observaciones = "Nueva observacion test";
        vehiculo1.runDuenio = "193982336";


        Vehiculo vehiculo2 = contratos.editarVehiculo(vehiculo1);

        logger.debug("Vehiculo from backend: {} {}", vehiculo2.patente, vehiculo2.observaciones);

        assertNotNull(vehiculo2);
    }

    @Test
    public void editarPersona(){

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratos = ice.getContratos();

        Persona persona1 = new Persona();
        persona1.uid =6;
        persona1.run = "11111111";
        persona1.nombre = "Alvaro Castillo";
        persona1.sexo = Sexo.VAR;
        persona1.email = "alvaro.castillo@alumnos.ucn.cl";
        persona1.categoriaPersona = CategoriaPersona.ESTUDIANTE;


        Persona persona2 = contratos.editarPersona(persona1);

        logger.debug("Persona from backend: {} {}", persona2.run, persona2.sexo);

        assertNotNull(persona2);
    }
}
