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

package cl.ucn.disc.pdis.parkingapp.repository;

import com.zeroc.IceInternal.Ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ucn.disc.pdis.parkingapp.repository.service.ZeroIce;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Acceso;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotAuthorizedException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotFoundException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Persona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Porteria;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;

public class Communicator {

    /*
    The logger.
     */
    public static Logger logger = LoggerFactory.getLogger(Communicator.class);

    /*
    The ICE connection.
     */
    private final ZeroIce ICE_CONNECTION;

    /*
    The operator who have all the contratos.
     */
    private static ContratosPrx operator;

    /**
     * Inicia la conexion.
     */
    public Communicator() {

        ICE_CONNECTION = new ZeroIce();
        ICE_CONNECTION.start();

        operator = ICE_CONNECTION.getContratos();

    }

    /**
     * Termina la conexion.
     */
    public void endCommunication(){

        operator = null;
        ICE_CONNECTION.stop();

    }

    /**
     * Se le solicitara al servidor que envie todos los vehiculos de la base de datos.
     * @return Una lista de vehiculos.
     * @throws ServerException En caso de un error interno en el servidor.
     */
    public Vehiculo[] obtenerVehiculos() throws ServerException {

        try{

            return operator.obtenerVehiculos();

        } catch (ServerException exception){

            throw exception;

        } catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }

    }

    /**
     * Se le solicitara al servidor que envie todas las personas de la base de datos.
     * @return Una lista de personas.
     * @throws ServerException
     */
    public Persona[] obtenerPersonas() throws ServerException {

        try{

            return operator.obtenerPersonas();

        } catch (ServerException exception){

            throw exception;

        } catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Dado una patente y la porteria de acceso se generara un registro de acceso en el servidor.
     * @param patente Patente del vehiculo.
     * @param porteria Nombre de la porteria
     * @return Acceso
     * @throws ServerException En caso de un error interno en el servidor.
     */
    public Acceso autorizarVehiculo(String patente, Porteria porteria) throws ServerException {

        try{

            return operator.autorizarVehiculo(patente,porteria);

        } catch (ServerException exception){

            throw exception;

        } catch (Exception e){

            throw e;
        }

    }




}
