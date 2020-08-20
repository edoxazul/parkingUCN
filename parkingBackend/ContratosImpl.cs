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
using System.Linq;
using Ice;
using Microsoft.Data.Sqlite;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using ParkingUcn.ZeroIce.model;
using Exception = System.Exception;

namespace ParkingBackend
{
    public class ContratosImpl : ContratosDisp_
    {
        /// <summary>
        /// Logger
        /// </summary>
        private readonly ILogger<ContratosImpl> _logger;

        /// <summary>
        /// Scope Factory
        /// </summary>
        private readonly IServiceScopeFactory _serviceScopeFactory;

        /// <summary>
        /// The Constructor
        /// </summary>
        /// <param name="logger"></param>
        /// <param name="serviceScopeFactory"></param>
        public ContratosImpl(ILogger<ContratosImpl> logger, IServiceScopeFactory serviceScopeFactory)
        {
            _logger = logger;
            _logger.LogDebug("Building ContratosImpl");
            _serviceScopeFactory = serviceScopeFactory;

            // Create the database
            _logger.LogInformation("Creating the Database ..");
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                parkingContext.Database.EnsureCreated();
                parkingContext.SaveChanges();
            }

            _logger.LogDebug("Done.");
        }

        
        /// <summary>
        /// Authorize a vehicle to go in or out of the campus.
        /// </summary>
        /// <param name="patente">Unique id of the vehicle</param>
        /// <param name="porteria">The entrance registration</param>
        /// <param name="current"></param>
        /// <returns>The access log for confirmation</returns>
        /// <exception cref="ServerException"></exception>
        public override Acceso autorizarVehiculo(string patente, Porteria porteria, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();

                    Acceso acceso = new Acceso();
                    acceso.patente = patente;
                    acceso.horaEntrada = DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss");
                    acceso.porteria = porteria;
                    
                    parkingContext.Accesos.Add(acceso);
                    parkingContext.SaveChanges();
                    
                    return acceso;
                }
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException("Internal server error.");
            }
        }


        /// <summary>
        /// Get all the vehicles from the person's run.
        /// </summary>
        /// <param name="current"></param>
        /// <returns>All vehicles related with the run</returns>
        /// <exception cref="ServerException">Internal server error</exception>
        public override Vehiculo[] obtenerVehiculos(Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    var vehiculos = parkingContext
                        .Vehiculos
                        .ToArray();

                    return vehiculos;
                }
            }

            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException("Internal server error.");
            }
        }

        public override Persona[] obtenerPersonas(Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    var personas = parkingContext
                        .Personas
                        .ToArray();

                    return personas;
                }
            }

            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException("Internal server error.");
            }
        }
    }
}