<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;

require 'Ice.php';
require_once "../../domain.php";

class PersonaController extends Controller
{
    public function persona()
    {
        return view('persona');
    }

    public function insertar(Request $request){
        $rut = $request->input("rut");
        $nombre = $request->input("nombre");
        $sexo = $request->input("sexo");
        $email = $request->input("email");
        $tipo = $request->input("tipo");

        //Insert data in DB
    }
}
