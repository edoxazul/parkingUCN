@extends('layouts.app')
@section('content')
    <div class="container mt-4 pt-4">
        <div class="col-md-12 justify-content-center">
            <div class="card border-secondary shadow">
                <div class="card-header h2 d-flex justify-content-center mb-4 bg-tertiary">
                    <div class="card-body">
                        <form action="{{action('PersonaController@insertar')}}" method="POST">

                            @include('Persona.partials.form')
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
