@csrf
<div class="form-group">
    {{ Form::label('rut', 'Rut:') }}
    {{ Form::label('rut','*', array('class' => 'text-danger'))}}
    {{ Form::text('rut', null, ['class' => 'form-control', 'id' => 'rut']) }}
</div>

<div class="form-group mt-4 text-center">
    {{ Form::submit('Eliminar Persona', ['class' => 'btn btn-secondary mb-4']) }}
</div>
