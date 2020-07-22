using System.Threading;
using System.Threading.Tasks;
using Ice;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using ParkingUcn.ZeroIce.model;

namespace ParkingBackend
{
    public class ParkingService: IHostedService
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
        /// Sistema 
        /// </summary>
        private readonly ContratosDisp_ _contratos;
        
        /// <summary>
        /// Logger
        /// </summary>
        private readonly ILogger<ParkingService> _logger;
        
        /// <summary>
        /// Constructor.
        /// </summary>
        /// <param name="logger">Logger</param>
        /// <param name="contratos">Contratos</param>
        public ParkingService(ILogger<ParkingService> logger, ContratosDisp_ contratos)
        {
            _logger = logger;
            _contratos = contratos;
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
            var adapter = _communicator.createObjectAdapterWithEndpoints(
                "Contratos",
                "tcp -z -t 15000 -p " + _port);
            
            // Register in the adapter
            adapter.add(_contratos, Util.stringToIdentity("Contratos"));
            
            adapter.activate();
            
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
    }
}