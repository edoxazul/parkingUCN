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
                    Ingresar Persona
                </div>
                <div class="card-body">
                    <form action="{{action('PersonaController@editarPost')}}" method="POST">
                        @csrf
                        <div class="form-group">
                            {{ Form::label('rut', 'Rut:') }}
                            {{ Form::label('rut','*', array('class' => 'text-danger'))}}
                            {{ Form::text('rut', $persona->run, ['class' => 'form-control', 'id' => 'rut','readonly']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('nombre', 'Nombre:') }}
                            {{ Form::text('nombre', $persona->nombre, ['class' => 'form-control','id' => 'nombre']) }}
                        </div>

                        <div class="form-group">

                            {{ Form::label('sexo','Sexo:')}}
                            {{ Form::select("sexo",["Hombre" => "Hombre",
                                                    "Mujer" => "Mujer",
                                                    "Otro" => "Otro"],
                            null,['class' => 'form-control', 'placeholder'=>'Seleccionar Sexo']) }}
                        </div>

                        <div class="form-group">

                            {{ Form::label('email', 'Email:') }}
                            {{ Form::label('userType','*', array('class' => 'text-danger'))}}
                            {{ Form::text('email', $persona->email, ['class' => 'form-control', 'id' => 'email']) }}
                        </div>

                        <div class="form-group">

                            {{ Form::label('tipo','Tipo de Persona:')}}
                            {{ Form::select("tipo",["Estudiante" => "Estudiante",
                                                    "Académico" => "Académico",
                                                    "Funcionario"=>"Funcionario"],
                            null,['class' => 'form-control', 'placeholder'=>'Seleccionar Tipo']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('unidad', 'Unidad:') }}
                            {{ Form::text('unidad', $persona->unidad, ['class' => 'form-control','id' => 'unidad']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('telefonoFijo', 'Teléfono Fijo:') }}
                            {{ Form::text('telefonoFijo', $persona->telefonoFijo, ['class' => 'form-control','id' => 'telefonoFijo']) }}
                        </div>

                        <div class="form-group">
                            {{ Form::label('telefonoMovil', 'Teléfono Movil:') }}
                            {{ Form::text('telefonoMovil', $persona->telefonoMovil, ['class' => 'form-control','id' => 'telefonoMovil']) }}
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