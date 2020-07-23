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
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Sexo;

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

        logger.debug("Data out: {} {} {}", persona1.nombre, persona1.run, persona1.sexo);
        Persona persona2 = contratos.registrarPersona(persona1);
        logger.debug("Data in: {} {} {}", persona2.nombre, persona2.run, persona2.sexo);

        assertNotNull(persona2);

        // Send the same person, should return Duplicate
        assertThrows(DuplicateDataException.class, () -> contratos.registrarPersona(persona1));

    }
}
