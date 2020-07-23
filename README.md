# ParkingUCN 
Project ParkingUCN for the signature Proyecto De Desarrollo e Integración de Soluciones, UCN.

## Authors
[Eduardo Alexis Alvarez Saldivia](https://github.com/edoxazul/) -      <eas010@alumnos.ucn.cl>

[Alvaro Lucas Castillo Calabacero](https://github.com/AlvaroCC96) -      <alvaro.castillo@alumnos.ucn.cl>

[Ignacio Fuenzalida Veas](https://github.com/NaChOoV) -      <ignacio.fuenzalida@alumnos.ucn.cl>

## Sections
* [Agenda-Scrapper](./agenda-scrapper)
* [Android APP](./parkingApp)
* [Web](./parkingWeb)
* [Back-end](./parkingBackend)

## Technologies

- [Java OpenJDK 11](https://openjdk.java.net/projects/jdk/11/).
- [Jetbrains IntelliJ](https://www.jetbrains.com/idea/nextversion/).
- [Gradle](https://gradle.org/).
- [ZeroIce](https://zeroc.com/).
- [PHP](https://www.php.net/).
- [Laravel Framework](https://laravel.com/).
- [.NET Core](https://dotnet.microsoft.com/).
- [EntityFramework Core](https://docs.microsoft.com/en-us/ef/core/).
- [Jetbrains Rider](https://www.jetbrains.com/rider/nextversion/).
- [SQLite](https://www.sqlite.org/).
- [Kotlin](https://developer.android.com/kotlin).
- [Android Studio Preview](https://developer.android.com/studio/preview).



## UML
![Imagén No Disponible](img/diagrama.png)
--- 
<details><summary>PlantUML</summary>
<p>

```

@startuml


class Persona{
    -uid: int
    -rut: string
    -nombre: string
    -unidad: string
    -email: string
    -telefonoMovil: string
    -categoriaPersona: CategoriaPersona
}

enum CategoriaPersona{
    FUNCIONARIO
    ACADEMICO
    ESTUDIANTE
}

class Vehiculo{
    -uid: int
    -patente: string
    -marca: string
    -modelo: string
    -anio: int
    -observaciones: string
    -runDuenio: string
}

class Program {
    main()
    CreateHostBuilde(String[] args): IHostBuilder
}

interface Contratos {
    verificarPersona(string run): Persona
    autorizarVehiculo(string patente, bool tipo): Vehiculo
    registrarPersona(Persona persona): Persona
    eliminarPersona(string run): Persona
    editarPersona(Persona persona): Persona
    registarVehiculo(Vehiculo vehiculo): Vehiculo
    eliminarVehiculo(Vehiculo vehiculo): Vehiculo
}
class ContratosImpl {

}

Program --> ContratosImpl: use
ContratosImpl --> Persona: use
ContratosImpl --> Vehiculo: use
Persona --> CategoriaPersona : use
ContratosImpl<|.. Contratos: implement

@enduml

```

</p>
</details>