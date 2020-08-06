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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ucn.disc.pdis.parkingucn.zeroice.model.Acceso;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Location;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotAuthorizedException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotFoundException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Persona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class ContratosTest {

    public static Logger logger = LoggerFactory.getLogger(ContratosTest.class);

    @Test
    public void verificarPersona() throws NotFoundException, ServerException {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratosPrx = ice.getContratos();

        Persona persona = contratosPrx.verificarPersona("193982336");

        logger.info("Waiting for person: 193982336");
        assertNotNull(persona);
        logger.debug("DONE: Persona found and returned! -> {} {} {}",
                persona.nombre,
                persona.run,
                persona.sexo);

        logger.info("Sending person who don't exist on the database.");
        assertThrows(NotFoundException.class,
                () -> contratosPrx.verificarPersona("999999999"));
        logger.debug("DONE: Exception reach for no person found!");

    }

    @Test
    public void obtenerVehiculo() throws NotFoundException {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratosPrx = ice.getContratos();

        logger.info("Waiting for person: 193982336");
        Vehiculo[] vehiculos = contratosPrx.obtenerVehiculos("193982336");


        assertEquals(vehiculos.length,2);
        logger.debug("DONE: Vehicles returned susccefully!");

        for(Vehiculo veh : vehiculos){
            logger.debug("{} {} {}",veh.patente,veh.marca,veh.modelo);
        }

        logger.info("Sending vehicle's run that don't exist on the database.");
        assertThrows(NotFoundException.class,
                () -> contratosPrx.obtenerVehiculos("999999999"));
        logger.debug("DONE: Exception reach for no vehicles found for that run!");

    }

    @Test
    public void autorizarVehiculo() throws NotAuthorizedException, ServerException {

        ZeroIce ice = new ZeroIce();
        ice.start();
        ContratosPrx contratosPrx = ice.getContratos();

        logger.info("Authorize a vehicle who want to OUT.");
        Acceso acceso = contratosPrx.autorizarVehiculo("CHLJ90", Location.OUT);
        assertNotNull(acceso);
        logger.debug("DONE: Vehicle authorized succefully to OUT!");
        logger.debug("ACCESS: {} {}", acceso.horaEntrada, acceso.patente);

        logger.info("Duplicating the same request.");
        assertThrows(NotAuthorizedException.class,
                () -> contratosPrx.autorizarVehiculo("CHLJ90", Location.OUT));
        logger.debug("DONE: Exception reach for not authorized vehicle!");

        logger.info("Authorize a vehicle who want to IN.");
        acceso = contratosPrx.autorizarVehiculo("CHLJ99", Location.IN);
        assertNotNull(acceso);
        logger.debug("DONE: Vehicle authorized succefully to IN!");
        logger.debug("ACCESS: {} {}", acceso.horaEntrada, acceso.patente);

        logger.info("Duplicating the same request.");
        assertThrows(NotAuthorizedException.class,
                () -> contratosPrx.autorizarVehiculo("CHLJ99", Location.IN));
        logger.debug("DONE: Exception reach for not authorized vehicle!");

    }
}
