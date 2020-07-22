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

            optionsBuilder.UseSqlite("Data Source=parking.db", options =>
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
                    telefonoMovil = "+56 9 66512572",
                    telefonoFijo = "null",
                    categoriaPersona = new CategoriaPersona()
                }

            );


        }


    }


}