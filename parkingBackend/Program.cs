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
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Hosting;
using ParkingUcn.ZeroIce;
using ParkingUcn.ZeroIce.model;

namespace ParkingBackend
{
    class Program
    {
        static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
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

                    // The logger
                    services.AddLogging();
    
                    // Yhe wait 4 finish
                    services.Configure<HostOptions>(option => 
                    {
                        option.ShutdownTimeout = System.TimeSpan.FromSeconds(15);
                    });
                    
                    // Contratos
                    services.AddSingleton<ContratosDisp_, ContratosImpl>();
                    
                    // ParkingService 
                    services.AddHostedService<ParkingService>();
                    
                    // The Parking Context
                    //services.AddDbContext<ParkingContext>();
                });
        }
    }
}
