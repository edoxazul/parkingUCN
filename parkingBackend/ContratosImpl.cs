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

using System;
using Ice;
using Microsoft.Extensions.Logging;

using ParkingUcn.ZeroIce.model;

namespace ParkingBackend
{
    public class ContratosImpl : ContratosDisp_
    {    
        /// <summary>
        /// Logger
        /// </summary>
        private readonly ILogger<ContratosImpl> _logger;

        /// <sumary>
        /// The Constructor
        /// </sumary>
        /// <param name="logger">Logger</param>
        public ContratosImpl(ILogger<ContratosImpl> logger)
        {
            _logger = logger;
            _logger.LogDebug("Building ContratosImpl");
        }
        
        /// <sumary>
        /// check if the person exists given a run
        /// </sumary>
        /// <param name="run">RUN of person</param>
        /// <param name="current">.</param>
        public override Persona verificarPersona(string run, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// authorize a vehicle given a patent
        /// </sumary>
        /// <param name="patente">patent of vehicle</param>
        /// <param name="current">.</param>
        /// <param name="tipo">type of person</param>
        public override Vehiculo autorizarVehiculo(string patente, bool tipo, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// register a person
        /// </sumary>
        /// <param name="persona">Person to be registered</param>
        /// <param name="current">.</param>
        public override Persona registrarPersona(Persona persona, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// delete a person given the run
        /// </sumary>
        /// <param name="run">RUN of person</param>
        /// <param name="current">.</param>
        public override Persona eliminarPersona(string run, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// Edit a person
        /// </sumary>
        /// <param name="persona">Person to be edited</param>
        /// <param name="current">.</param>
        public override Persona editarPersona(Persona persona, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// register a vehicle
        /// </sumary>
        /// <param name="vehiculo">Vehicle to be registered</param>
        /// <param name="current">.</param>
        public override Vehiculo registrarVehiculo(Vehiculo vehiculo, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// delete a vehicle given the run
        /// </sumary>
        /// <param name="patente">Patent of vehicle</param>
        /// <param name="current">.</param>
        public override Vehiculo eliminarVehiculo(string patente, Current current = null)
        {
            throw new NotImplementedException();
        }
        
        /// <sumary>
        /// Edit a vehicle
        /// </sumary>
        /// <param name="vehiculo">Vehicle to be edited</param>
        /// <param name="current">.</param>
        public override Vehiculo editarVehiculo(Vehiculo vehiculo, Current current = null)
        {
            throw new NotImplementedException();
        }
    }
}