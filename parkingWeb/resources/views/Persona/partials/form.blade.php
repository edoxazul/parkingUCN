@csrf
<div class="form-group">

    {{ Form::label('rut', 'Rut:') }}
    {{ Form::label('rut','*', array('class' => 'text-danger'))}}
    {{ Form::text('rut', null, ['class' => 'form-control', 'id' => 'rut']) }}
</div>

<div class="form-group">

    {{ Form::label('nombre', 'Nombre:') }}
    {{ Form::text('nombre', null, ['class' => 'form-control','id' => 'nombre']) }}
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
    {{ Form::text('email', null, ['class' => 'form-control', 'id' => 'email']) }}
</div>

<div class="form-group">

    {{ Form::label('tipo','Tipo de Persona:')}}
    {{ Form::select("tipo",["Estudiante" => "Estudiante",
                            "Académico" => "Académico",
                            "Funcionario"=>"Funcionario"],
    null,['class' => 'form-control', 'placeholder'=>'Seleccionar Tipo']) }}
</div>

<div class="form-group mt-4 text-center">

    {{ Form::submit('Ingresar Persona', ['class' => 'btn btn-secondary mb-4']) }}
</div>
