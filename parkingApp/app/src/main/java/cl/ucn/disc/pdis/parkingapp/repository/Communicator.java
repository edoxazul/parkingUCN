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

import cl.ucn.disc.pdis.parkingapp.repository.service.ZeroIce;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotAuthorizedException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.NotFoundException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;

public class Communicator {

    private final ZeroIce ICE_CONNECTION;

    private static ContratosPrx operator;

    public Communicator() {

        ICE_CONNECTION = new ZeroIce();
        ICE_CONNECTION.start();

        operator = ICE_CONNECTION.getContratos();

    }

    public void endCommunication(){

        operator = null;
        ICE_CONNECTION.stop();

    }

    public boolean verificarPersona(String run) {

        try{

            operator.verificarPersona(run);

        } catch (NotFoundException exception){

            return false;

        } catch (ServerException exception){

        } catch (Exception exception){

        }

        return true;
    }

    public Vehiculo[] obtenerVehiculos(){

        return null;
    }

    public boolean autorizarVehiculo(){


        return true;

    }




}
