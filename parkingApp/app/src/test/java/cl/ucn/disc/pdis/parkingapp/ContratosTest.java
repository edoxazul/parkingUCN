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

import cl.ucn.disc.pdis.parkingapp.repository.Communicator;
import cl.ucn.disc.pdis.parkingapp.repository.service.ZeroIce;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Acceso;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Location;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;


import static org.junit.Assert.assertNotNull;


public class ContratosTest {

    public static Logger logger = LoggerFactory.getLogger(ContratosTest.class);


    @Test
    public void obtenerVehiculos() throws ServerException {

        Communicator communicator = new Communicator();
        Vehiculo[] vehiculos = communicator.obtenerVehiculos();

        logger.debug("DONE: Vehicles returned susccefully!");

        for (Vehiculo veh : vehiculos) {
            logger.debug("{} {} {}", veh.patente, veh.marca, veh.modelo);
        }

    }

    @Test
    public void autorizarVehiculo() throws ServerException {


    }
}
