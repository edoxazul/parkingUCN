@extends('layouts.app')
<head>
    <title>Laravel</title>
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,600" rel="stylesheet">

    <!-- Styles -->
    <style>
        html, body {
            background-color: #fff;
            color: #09258a;
            font-family: 'Nunito', sans-serif;
            font-weight: 200;
            height: 100vh;
            margin: 0;
        }

        .full-height {
            height: 50vh;
        }

        .flex-center {
            align-items: center;
            display: flex;
            justify-content: center;
        }

        .position-ref {
            position: relative;
        }

        .top-right {
            position: absolute;
            right: 10px;
            top: 15px;
        }

        .content {
            text-align: center;
        }

        .title {
            font-size: 84px;
        }

        .links > a {
            color: #636b6f;
            padding: 0 25px;
            font-size: 13px;
            font-weight: 600;
            letter-spacing: .1rem;
            text-decoration: none;
            text-transform: uppercase;
        }

        .m-b-md {
            margin-bottom: 30px;
        }
    </style>
</head>
@section('content')
    <div class="flex-center position-ref full-height">
        <div class="container mt-4 p-4">
            @if (session('alert'))
                <div class="alert alert-danger">
                    {{ session('alert') }}
                </div>
            @endif
            @if (session('success'))
                <div class="alert alert-success">
                    {{ session('success') }}
                </div>
            @endif
            <div class="row justify-content-center">
                <div class="title m-b-md">
                    Parking UCN
                </div>
                <div class="col-md-8 justify-content-center">
                    <div class="card border-secondary shadow">
                        <div class="card-header h2 bg-tertiary">
                            <div class="row">
                                <div class="col-sm-7">
                                    Listado de Accesos
                                </div>
                                <div class="col">
                                    <li class="btn btn-default">
                                        <a class='btn btn-info' href='/exportar'>Exportar Accesos</a>
                                    </li>
                                </div>
                            </div>

                        </div>
                        <div class="card-body">
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>Hora de Entrada</th>
                                    <th>Patente</th>
                                    <th>Portería</th>
                                </tr>
                                </thead>
                                <tbody>
                                @foreach ($access as $acceso)
                                    <tr>
                                        <td> {{ $acceso->horaEntrada }} </td>
                                        <td> {{ $acceso->patente }} </td>
                                        <td> @if($acceso->porteria == 0)
                                                Sur
                                             @elseif($acceso->porteria ==1)
                                                Mancilla
                                             @elseif($acceso->porteria==2)
                                                 Sangra
                                        @endif
                                        </td>
                                    </tr>
                                @endforeach
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
