<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use model\Acceso;

require_once 'Ice.php';
//for fixed dir from domain.php
require_once base_path() . "./../domain.php";

class AccesoController extends Controller
{

    public function ingresarView()
    {
        return view('Acceso.registrarAcceso');
    }

    public function insertar(Request $request)
    {
        // Data Request
        $patente = $request->input("patente");
        $horaEntrada = $request->input("horaEntrada");

        // ZeroIce
        $ice = null;
        $contratos = null;

        try {
            $ice = \Ice\Initialize();
            $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
            $contratos = \model\ContratosPrxHelper::checkedCast($proxy);

            $acceso = new Acceso();
            $acceso->patente = $patente;
            $acceso->horaEntrada = $horaEntrada;

            $acceso = $contratos->autorizarVehiculo($acceso);
            // The rut not exist in database
            if ($acceso == null) {
                return redirect()->back()->with('alert', 'Error al añadir el Acceso');
            }
            if ($ice) {
                $ice->destroy();
            }
            return redirect()->back()->with('success', 'Acceso registrado correctamente!');
        } catch (Exception $ex) {
            return redirect()->back()->with('alert', 'Error al agregar!');
        }

    }

    public function eliminarView()
    {
        return view("Acceso.eliminarAcceso");
    }

    public function eliminar(Request $request)
    {

        // Data Request
        $patente = $request->input("patente");
        str_replace("-", "", $patente);

        // ZeroIce
        $ice = null;
        $contratos = null;

        try {
            $ice = \Ice\Initialize();
            $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
            $contratos = \model\ContratosPrxHelper::checkedCast($proxy);

            // Elimination of acces
            $acceso = $contratos->eliminarAcceso($patente);

            // The rut not exist in database
            if ($acceso == null) {
                return redirect()->back()->with('alert', 'Acceso No Registrado!');
            }

            if ($ice) {
                $ice->destroy();
            }

            // Elimination completed
            return redirect()->back()->with('success', 'Acceso Eliminado Correctamente!');

        } catch (Exception $ex) {
            echo $ex;
        }
    }


}
