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
using System.Threading;
using System.Threading.Tasks;
using Ice;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using ParkingUcn.ZeroIce.model;

namespace ParkingBackend
{
    public class ParkingService: IHostedService,IDisposable
    {    
        /// <summary>
        /// Port for ZeroICE
        /// </summary>
        private readonly int _port = 4000;
        
        /// <summary>
        /// Communicator
        /// </summary>
        private readonly Communicator _communicator;

        /// <summary>
        /// Contratos 
        /// </summary>
        private readonly ContratosDisp_ _contratos;
        
        /// <summary>
        /// Sistema 
        /// </summary>
        private readonly TheSystemDisp_ _thesystem;
        
        /// <summary>
        /// Logger
        /// </summary>
        private readonly ILogger<ParkingService> _logger;
        
        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="logger">Logger</param>
        /// <param name="contratos">Contratos</param>
        public ParkingService(ILogger<ParkingService> logger, ContratosDisp_ contratos, TheSystemDisp_ thesystem)
        {
            _logger = logger;
            _contratos = contratos;
            _thesystem = thesystem;
            _communicator = BuildCommunicator();
        }
        
        /// <summary>
        /// Build Communicator
        /// </summary>
        /// <returns>Communicator</returns>
        private Communicator BuildCommunicator()
        {
            _logger.LogDebug("Initializing communicator");
            
            // ZeroC properties
            Properties properties = Util.createProperties();
            InitializationData initializationData = new InitializationData();
            initializationData.properties = properties;
            
            return Ice.Util.initialize(initializationData);
        }
        
        /// <summary>
        /// Start async task
        /// </summary>
        /// <param name="cancellationToken"></param>
        /// <returns></returns>
        public Task StartAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("ParkingService started.");
            
            
            // Ice adapter
            var contratosAdapter = _communicator.createObjectAdapterWithEndpoints(
                "Contratos",
                "tcp -z -t 15000 -p " + _port);
            var theSystemAdapter = _communicator.createObjectAdapterWithEndpoints(
                "TheSystem",
                "tcp -z -t 15000 -p " + (_port + 20)  );
            
            
            theSystemAdapter.add(_thesystem, Util.stringToIdentity("TheSystem"));
            
            ObjectPrx proxy = contratosAdapter.add(_contratos, Util.stringToIdentity("Contratos"));

            _logger.LogInformation("PROXY INFO:");
            _logger.LogInformation(proxy.ToString());
            
            contratosAdapter.activate();
            theSystemAdapter.activate();
            
            return Task.CompletedTask;
        }

        /// <summary>
        /// Stop async task
        /// </summary>
        /// <param name="cancellationToken"></param>
        /// <returns></returns>
        public Task StopAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("ParkingService stopped.");
            
            // Stop communicator
            _communicator.shutdown();
            return Task.CompletedTask;
        }

        /// <summary>
        /// Clear the memory
        /// </summary>
        public void Dispose()
        {
            _communicator.destroy();
        }
    }
}