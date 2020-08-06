<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Boostrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.1/css/bootstrap.min.css" integrity="sha384-VCmXjywReHh4PwowAiWNagnWcLhlEJLA5buUprzK8rxFgeH0kww/aWY76TfkUoSX" crossorigin="anonymous">
        <title>Vista Persona</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row  p-3">
                <div class ="col-sm-3 p-3 bg-success">
                    <form action="{{action('PersonaController@insertar')}}" method="POST">
                        {{csrf_field()}}
                        <label for="rut">RUT: </label>
                        <input type="text" id="rut" name="rut"><br><br>

                        <dlabel for="nombre">Nombre: </dlabel>
                        <input type="text" id="nombre" name="nombre"><br><br>

                        <label for="sexo">Sexo: </label>
                        <select name="sexo" id="sexo">
                            <option value="Hombre"> Hombre</option>
                            <option value="Mujer"> Mujer</option>
                            <option value="Otro"> Otro</option>
                        </select><br><br>

                        <label for="email">Email: </label>
                        <input type="text" id="email" name="email"><br><br>

                        <label for="tipo">Tipo De Persona: </label>
                        <select name="tipo" id="tipo">
                            <option value="Estudiante"> Estudiante</option>
                            <option value="Academico"> Academico</option>
                        </select><br><br>

                        <button type="submit" value="Enviar Datos">Ingresar Persona</button>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
