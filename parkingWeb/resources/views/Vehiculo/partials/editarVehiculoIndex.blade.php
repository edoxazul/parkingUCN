@csrf
<div class="form-group">
    {{ Form::label('patente', 'Patente:') }}
    {{ Form::label('patente','*', array('class' => 'text-danger'))}}
    {{ Form::text('patente', null, ['class' => 'form-control', 'id' => 'patente']) }}
</div>

<div class="form-group mt-4 text-center">
    {{ Form::submit('Buscar Vehiculo', ['class' => 'btn btn-secondary mb-4']) }}
</div>
