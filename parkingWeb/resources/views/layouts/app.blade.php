<!DOCTYPE html>

<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>{{ config('app.name', 'Laravel') }}</title>

    <!-- Fonts -->
    <link rel="dns-prefetch" href="//fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">

    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <link rel="stylesheet" href="{{ asset('css/styles.css') }}">

    <!-- Bootstrap select styles -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

</head>

<body>
<div id="app">
    <style>
        .dropdown:hover > .dropdown-menu {
            display: block;
        }
    </style>

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">


        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link text-white font-weight-bold btn-lg" href="{{ url('/') }}">Inicio</a>
                </li>
                <li class="nav-item dropdown">
                    <div class="nav-link dropdown-toggle text-white btn-lg" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="false" aria-expanded="false"> Persona
                    </div>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="{{ url('/ingresarPersona') }}">Ingresar Persona</a>
                        <a class="dropdown-item" href="{{ url('/eliminarPersona') }}">Eliminar Persona</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <div class="nav-link dropdown-toggle text-white btn-lg" href="#" id="navbarDropdown" role="button"
                         data-toggle="dropdown" aria-haspopup="false" aria-expanded="false"> Vehiculo
                    </div>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <!--<a class="dropdown-item" href="{{ url('/ingresarPersona') }}">Ingresar Persona</a>-->
                        <a class="dropdown-item" href="{{ url('/eliminarVehiculo') }}">Eliminar Vehiculo</a>
                    </div>
                </li>
                <li class="nav-item active">
                    <a class="nav-link text-white btn-lg" href="{{ url('/test') }}">Test Connection</a>
                </li>
            </ul>
        </div>
    </nav>
    <main class="py-4">
        @yield('content')
    </main>

</div>


<!--Archivos JS-->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>


<!-- Bootstrap Dropdown Hover JS -->
<script src="js/bootstrap-dropdownhover.min.js"></script>

<!--Bootstrap Select JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

@yield('scripts')


</body>

</html>
