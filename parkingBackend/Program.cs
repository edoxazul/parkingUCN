using System;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Hosting;
using ParkingUcn.ZeroIce;
using ParkingUcn.ZeroIce.model;

namespace ParkingBackend.Server
{
    class Program
    {
        static int Main(string[] args)
        {

            return 0;
        }



        public static IHostBuilder CreateHostBuilder(string[] args)
        {
            return Host
                .CreateDefaultBuilder(args)
                // Development, Staging, Production
                .UseEnvironment("Development")
                // Logging configuration
                .ConfigureLogging(logging =>
                {
                    logging.ClearProviders();
                    logging.AddConsole(options =>
                    {
                        options.IncludeScopes = true;
                        options.TimestampFormat = "[yyyyMMdd.HHmmss.fff]";
                        options.DisableColors = false;
                    });
                    logging.SetMinimumLevel(LogLevel.Trace);
                })
                // Enable Control+C listener
                .UseConsoleLifetime()
                // Service inside the DI
                .ConfigureServices((hostContext, services) =>
                {    
                    // TheSystem
                    services.AddSingleton<TheSystemDisp_, TheSystemImpl>();
                    // Contratos
                    services.AddSingleton<ContratosDisp_, ContratosImpl>();
                    // The Fivet Context
                    services.AddDbContext<ParkingContext>();
                    // The FivetService
                    services.AddHostedService<ParkingService>();
                    // The logger
                    services.AddLogging();
    
                    // Yhe wait 4 finish
                    services.Configure<HostOptions>(option => 
                    {
                        option.ShutdownTimeout = System.TimeSpan.FromSeconds(15);
                    });       
                });
        }
    }
}
