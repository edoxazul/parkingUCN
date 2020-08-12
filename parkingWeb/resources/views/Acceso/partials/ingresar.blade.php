@csrf

<div class="form-group">

    {{ Form::label('patente', 'Patente:') }}
    {{ Form::label('patente','*', array('class' => 'text-danger'))}}
    {{ Form::text('patente', null, ['class' => 'form-control', 'id' => 'patente']) }}
</div>

<div class="form-group">

    {{ Form::label('location', 'Ubicación:') }}
    {{ Form::label('location','*', array('class' => 'text-danger'))}}
    {{ Form::select("location",["IN" => "Dentro del Campus",
                            "OUT" => "Fuera del Campus"],
    null,['class' => 'form-control', 'placeholder'=>'Seleccionar Acceso' ,'id' => 'location']) }}
</div>


<div class="form-group mt-4 text-center">
    {{ Form::submit('Agregar Acceso', ['class' => 'btn btn-secondary mb-4']) }}
</div>
