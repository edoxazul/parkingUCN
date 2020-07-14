# Agenda-scrapper
Example webscrapper with jsoup for the signature Proyecto De Desarrollo e Integración de Soluciones, UCN.

## Method 1 
Save data in csv file

## Method 2
Save data in a Sqlite database (by Patricio Araya)

##  
Eduardo Alexis Alvarez Saldivia

Alvaro Lucas Castillo Calabacero

Ignacio Fuenzalida Veas

## UML
![Imagén No Disponible](img/diagram.png)
--- 
@startuml
class Main{
    main(String[])
}

class ScrapperCsv{
    - theUrl: String
    - random: Random  
    - ini: int
    - end: int
    - fileWriter: FileWriter
    {static} - scrapperCsv: ScrapperCsv 
    
    ScrapperCsv()
    {static} getInstance(): ScrapperRut
    scrapperToCsv(): void
}

class ScrapperRut {
    - url: String
    - reader: CSVReader
    - writer: CSVWriter
    - random: Random
    {static} - scrapperRut: ScrapperRut
    
    ScrapperRut()
    {static} getInstance(): ScrapperRut
    scrapperToRut(): void 
    
}

Main --> ScrapperCsv : <<use>>
Main --> ScrapperRut : <<use>>
@enduml