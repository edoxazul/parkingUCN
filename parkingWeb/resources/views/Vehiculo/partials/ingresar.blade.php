@csrf
<div class="form-group">

    {{ Form::label('patente', 'Patente:') }}
    {{ Form::label('patente','*', array('class' => 'text-danger'))}}
    {{ Form::text('patente', null, ['class' => 'form-control', 'id' => 'patente']) }}
</div>

<div class="form-group">
    {{ Form::label('marca', 'Marca:') }}
    {{ Form::text('marca', null, ['class' => 'form-control','id' => 'marca']) }}
</div>

<div class="form-group">

    {{ Form::label('modelo', 'Modelo:') }}
    {{ Form::text('modelo', null, ['class' => 'form-control', 'id' => 'modelo']) }}
</div>

<div class="form-group">

    {{ Form::label('anio','Año:')}}
    {{ Form::text('anio', null,['class' => 'form-control', 'id'=>'anio']) }}
</div>

<div class="form-group">
    {{ Form::label('observaciones', 'Observaciones:') }}
    {{ Form::text('observaciones', null, ['class' => 'form-control','id' => 'observaciones']) }}
</div>

<div class="form-group">
    {{ Form::label('runDuenio', 'Rut del Dueño:') }}
    {{ Form::text('runDuenio', null, ['class' => 'form-control','id' => 'runDuenio']) }}
</div>

<div class="form-group">
    {{ Form::label('location', 'Ubicación del Vehículo:') }}
    {{ Form::select('location', ["IN" => "Dentro del Campus",
                                  "OUT" => "Fuera del Campus"],
    null, ['class' => 'form-control','placeholder' => 'Seleccionar Ubicación','id'=>'location']) }}
</div>

<div class="form-group mt-4 text-center">
    {{ Form::submit('Ingresar Vehiculo', ['class' => 'btn btn-secondary mb-4']) }}
</div>