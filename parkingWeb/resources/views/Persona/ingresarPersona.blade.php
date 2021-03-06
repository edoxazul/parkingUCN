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
                    <form action="{{action('PersonaController@insertar')}}" method="POST">
                        @include('Persona.partials.ingresar')
                    </form>
                </div>
            </div>
        </div>
    </div>
@endsection
