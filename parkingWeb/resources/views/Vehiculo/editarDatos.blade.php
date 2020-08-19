@extends('layouts.app')
@section('content')
    <div class="container mt-4 pt-4">
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
        <div class="col-md-12 justify-content-center">
            <div class="card border-secondary shadow">
                <div class="card-header h2 d-flex justify-content-center mb-4 bg-tertiary">
                    Editar Vehiculo
                </div>
                <div class="card-body">
                    <form action="{{action('VehiculoController@editarPost')}}" method="POST">
                        @csrf
                        <div class="form-group">
                            {{ Form::label('patente', 'Patente:') }}
                            {{ Form::label('patente','*', array('class' => 'text-danger'))}}
                            {{ Form::text('patente', $vehiculo->patente, ['class' => 'form-control', 'id' => 'patente','readonly']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('runDuenio', 'Rut Dueño:') }}
                            {{ Form::text('runDuenio', $vehiculo->runDuenio, ['class' => 'form-control','id' => 'runDuenio']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('marca', 'Marca:') }}
                            {{ Form::text('marca', $vehiculo->marca, ['class' => 'form-control','id' => 'marca']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('modelo', 'Modelo:') }}
                            {{ Form::text('modelo', $vehiculo->modelo, ['class' => 'form-control','id' => 'modelo']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('anio', 'Año:') }}
                            {{ Form::number('anio', $vehiculo->anio, ['class' => 'form-control', 'id' => 'anio' , 'min'=>0]) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('location', 'Ubicación del Vehículo:') }}
                            {{ Form::select('location', ["IN" => "Dentro del Campus",
                                                          "OUT" => "Fuera del Campus"],
                            null, ['class' => 'form-control','placeholder' => 'Seleccionar Ubicación']) }}
                        </div>


                        <div class="form-group">
                            {{ Form::label('observaciones', 'Observaciones:') }}
                            {{ Form::text('observaciones', $vehiculo->observaciones, ['class' => 'form-control','id' => 'observaciones']) }}
                        </div>

                        <div class="form-group mt-4 text-center">

                            {{ Form::submit('Confirmar Cambios', ['class' => 'btn btn-secondary mb-4']) }}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
@endsection