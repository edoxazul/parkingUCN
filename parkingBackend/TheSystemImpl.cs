﻿/*
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
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using ParkingUcn.ZeroIce.model;
using Exception = Ice.Exception;

namespace ParkingBackend
{
    public class TheSystemImpl : TheSystemDisp_
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
        public TheSystemImpl(ILogger<ContratosImpl> logger, IServiceScopeFactory serviceScopeFactory)
        {
            _logger = logger;
            _logger.LogDebug("Building TheSystemImpl");
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
        
        
        
        /// <sumary>
        /// register a person
        /// </sumary>
        /// <param name="persona">Person to be registered</param>
        /// <param name="current">.</param>
        public override Persona registrarPersona(Persona persona, Current current = null) 
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    parkingContext.Personas.Add(persona);
                    parkingContext.SaveChanges();
                    return parkingContext.Personas.FirstOrDefault(per => per.run == persona.run);
                }
            }
            catch (DbUpdateException exception)
            {
                _logger.LogDebug("Error adding : {}",exception.InnerException);
                return null;
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}",exception.InnerException);
                throw new ServerException("Unknow internal server error.");
            }
        }

        /// <sumary>
        /// Delete a person given the run
        /// </sumary>
        /// <param name="run">RUN of person</param>
        /// <param name="current">.</param>
        public override Persona eliminarPersona(string run, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    Persona persona = parkingContext.Personas
                        .FirstOrDefault(p => p.run == run);

                    if (persona != null)
                    {
                        parkingContext.Personas.Remove(persona);
                        parkingContext.SaveChanges();
                    }
                    return persona;
                }
            }
            catch (SqliteException exception)
            {
                _logger.LogDebug("Error deleting : {}",exception.InnerException);
                throw new NullReferenceException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                throw new ServerException();
            }
        }

        /// <sumary>
        /// Edit a person
        /// </sumary>
        /// <param name="persona">Person to be edited</param>
        /// <param name="current">.</param>
        public override Persona editarPersona(Persona persona, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();

                    Persona personaBD = parkingContext.Personas
                        .FirstOrDefault(p => p.run == persona.run);

                    if (personaBD == null)
                    {
                        return null;
                    }

                    personaBD.email = persona.email;
                    personaBD.nombre = persona.nombre;
                    personaBD.sexo = persona.sexo;
                    personaBD.run = persona.run;
                    personaBD.unidad = persona.unidad;
                    
                    parkingContext.SaveChanges();
                    return personaBD; 
                }
            }
            catch (SqliteException exception)
            {
                _logger.LogDebug("Error deleting : {}",exception.InnerException);
                throw new NullReferenceException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                throw new ServerException();
            } 
        }

        /// <sumary>
        /// register a vehicle
        /// </sumary>
        /// <param name="vehiculo">Vehicle to be registered</param>
        /// <param name="current">.</param>
        public override Vehiculo registrarVehiculo(Vehiculo vehiculo, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();

                    // Check if there is a person with the run on Personas Table
                    var persona = parkingContext.Personas.FirstOrDefault(per => per.run == vehiculo.runDuenio);
                    if (persona == null || persona.run != vehiculo.runDuenio)
                    {
                        throw new RunRelationNotFoundException();
                    }

                    parkingContext.Vehiculos.Add(vehiculo);
                    parkingContext.SaveChanges();
                    return parkingContext.Vehiculos.FirstOrDefault(veh => veh.patente == vehiculo.patente);
                }
            }
            catch (DbUpdateException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                return null;
            }
            catch (RunRelationNotFoundException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                throw new RunRelationNotFoundException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                throw new ServerException("Unknown internal server error.");
            }
        }

        /// <sumary>
        /// delete a vehicle given the run
        /// </sumary>
        /// <param name="patente">Patent of vehicle</param>
        /// <param name="current">.</param>
        public override Vehiculo eliminarVehiculo(string patente, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {    
                    _logger.LogDebug("Patente:" + patente);
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    Vehiculo vehiculo = parkingContext.Vehiculos
                        .FirstOrDefault(v => v.patente == patente);

                    if (vehiculo != null)
                    {
                        parkingContext.Vehiculos.Remove(vehiculo);
                        parkingContext.SaveChanges();
                    }
                    return vehiculo;
                }
            }
            catch (SqliteException exception)
            {
                _logger.LogDebug("Error deleting : {}",exception.InnerException);
                throw new NullReferenceException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                throw new ServerException("Unknown internal server error.");
            }
        }

        /// <sumary>
        /// Edit a vehicle
        /// </sumary>
        /// <param name="vehiculo">Vehicle to be edited</param>
        /// <param name="current">.</param>
        public override Vehiculo editarVehiculo(Vehiculo vehiculo, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    ParkingContext parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    

                    Vehiculo vehiculoBD = parkingContext.Vehiculos
                        .FirstOrDefault(v => v.patente == vehiculo.patente);

                    if (vehiculoBD == null)
                    {
                        return null;
                    }

                    vehiculoBD.patente = vehiculo.patente;
                    vehiculoBD.marca = vehiculo.marca;
                    vehiculoBD.modelo = vehiculo.modelo;
                    
                    vehiculoBD.observaciones = vehiculo.observaciones;
                    vehiculoBD.runDuenio = vehiculo.runDuenio;
                    vehiculoBD.location = vehiculo.location;

                    vehiculoBD.anio = vehiculo.anio;
                    parkingContext.SaveChanges();
                    return vehiculoBD;
                }
            }
            catch (SqliteException exception)
            {
                _logger.LogDebug("Error editing : {}",exception.InnerException);
                return null;
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                return null;
            }
        }

        public override long getDelay (long time, Current current = null)
        {
            return (int) DateTimeOffset.UtcNow.ToUnixTimeMilliseconds() - time;
        }

        public override Vehiculo getVehiculo(string patente, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();

                    // Check if there is a person with the run on Personas Table
                    var vehiculo = parkingContext.Vehiculos.FirstOrDefault(v => v.patente == patente);
                    return vehiculo;
                }
            }
            catch (DbUpdateException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                return null;
            }
            catch (RunRelationNotFoundException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                return null;
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                return null;
            }
        }

        public override Acceso[] getAllAccess(Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    var accesos = parkingContext
                                    .Accesos.ToArray();

                    return accesos;
                }
            }
            catch (NotFoundException exception)
            {
                _logger.LogDebug("Error finding : {}",exception);
                throw new NotFoundException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException();
            }
        }
        public override Persona[] getAllPersonas(Current current = null)
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
            catch (NotFoundException exception)
            {
                _logger.LogDebug("Error finding : {}",exception);
                throw new NotFoundException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException();
            }
        }

        public override Vehiculo[] getAllVehiculos(Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();
                    var vehiculos = parkingContext
                                    .Vehiculos.ToArray();

                    return vehiculos;
                }
            }
            catch (NotFoundException exception)
            {
                _logger.LogDebug("Error finding : {}",exception);
                throw new NotFoundException();
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception);
                throw new ServerException();
            }
        }

        public override Persona getPersona(string rut, Current current = null)
        {
            try
            {
                using var scope = _serviceScopeFactory.CreateScope();
                {
                    var parkingContext = scope.ServiceProvider.GetService<ParkingContext>();

                    // Check if there is a person with the run on Personas Table
                    var persona = parkingContext.Personas.FirstOrDefault(per => per.run == rut);
                    return persona;
                }
            }
            catch (DbUpdateException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                return null;
            }
            catch (RunRelationNotFoundException exception)
            {
                _logger.LogDebug("Error adding : {}", exception.InnerException);
                return null;
            }
            catch (Exception exception)
            {
                _logger.LogDebug("Server Error : {}", exception.InnerException);
                return null;
            }
        }
    }
}