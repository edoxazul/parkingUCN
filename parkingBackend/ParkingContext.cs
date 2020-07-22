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
using System.Reflection;
using ParkingUcn.ZeroIce.model;
using Microsoft.EntityFrameworkCore;

namespace ParkingBackend
{

    public class ParkingContext : DbContext
    {
        public DbSet<Persona> Personas {get; set;}
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {

            optionsBuilder.UseSqlite(@"Data Source=parking.db", options =>
            {
                options.MigrationsAssembly(Assembly.GetExecutingAssembly().FullName);
            });
            base.OnConfiguring(optionsBuilder);
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Persona>( p => 
            {

                // Primary 
                p.HasKey(p => p.uid);

                // Index in rut
                p.Property(p => p.run).IsRequired();
                p.HasIndex(p => p.run).IsUnique();
                // Index in Email
                p.Property(p => p.email).IsRequired();
            });

            // Insert the data
            modelBuilder.Entity<Persona>().HasData(
                new Persona(){
                    uid = 1,
                    run = "193982336",
                    nombre = "Ignacio Fuenzalida Veas",
                    unidad = "null",
                    email = "fuenzalida.veas@gmail.com",
                    telefonoMovil = "+56 9 9978635",
                    telefonoFijo = "(55) 23512572",
                    categoriaPersona = new CategoriaPersona()
                }
            );
        }
    }
}