@extends('layouts.app')
@section('content')
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
            <div class="col-md-8 justify-content-center">
                <div class="card border-secondary shadow">
                    <div class="card-header h2 bg-tertiary">
                        Listado de Personas
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Rut</th>
                                <th>Nombre</th>
                            </tr>
                            </thead>
                            <tbody>
                            @foreach ($personas as $persona)
                                <tr>
                                    <td> {{ $persona->run }} </td>
                                    <td> {{ $persona->nombre }} </td>
                                </tr>
                            @endforeach
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection