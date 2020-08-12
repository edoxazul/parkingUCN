<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use http\Url;
use model\Persona;

require_once 'Ice.php';
//for fixed dir from domain.php
require_once base_path() . "./../domain.php";

class VehiculoController extends Controller
{

    public function ingresarView()
    {
        return view('Vehiculo.ingresarVehiculo');

    }

    public function insertar(Request $request)
    {
        // Data Request
        $patente = $request->input("patente");
        $patente = strtoupper($patente);

        // Validation for len of patente.
        if (strlen($patente) > 7) {
            return redirect()->back()->with('alert', 'Patente Mal Ingresada!');
        }

        $marca = $request->input("marca");
        $modelo = $request->input("modelo");
        $anio = $request->input("anio");
        $observaciones = $request->input("observaciones");
        $runDuenio = $request->input('runDuenio');
        $location = $request->input('location');

        $locationVal = 0;


        // ZeroIce
        $ice = null;
        $theSystem = null;

        try {
            $ice = \Ice\Initialize();
            $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
            $theSystem = \model\TheSystemPrxHelper::checkedCast($proxy);

            //Verification of location;
            if ($location == 'IN') {
                $locationVal = 0;
            } elseif ($location == 'OUT') {
                $locationVal = 1;
            } else {
                $locationVal = 2;
            }


            $vehiculo = new Vehiculo();
            $vehiculo->patente = $patente;
            $vehiculo->marca = $marca;
            $vehiculo->modelo = $modelo;
            $vehiculo->anio = $anio;
            $vehiculo->observaciones = $observaciones;
            $vehiculo->runDuenio = $runDuenio;
            $vehiculo->location = $locationVal;

            $vehiculo = $theSystem->registrarVehiculo($vehiculo);
            // The rut not exist in database
            if ($vehiculo == null) {
                return redirect()->back()->with('alert', 'Error al añadir el vehiculo');
            }
            if ($ice) {
                $ice->destroy();
            }
            return redirect()->back()->with('success', 'Vehiculo Añadido Correctamente!');
        } catch (Exception $ex) {
            return redirect()->back()->with('alert', 'Error al agregar!');
        }
    }

    public function eliminarView()
    {
        return view('Vehiculo.eliminarVehiculo');
    }

    public function eliminar(Request $request)
    {

        // Data Request
        $patente = $request->input("patente");
        $patente = strtoupper($patente);

        // Validation for len of patente.
        if (strlen($patente) > 7) {
            return redirect()->back()->with('alert', 'Patente Mal Ingresada!');
        }
        // Special characters are not validated, because the patent has a hyphen

        // ZeroIce
        $ice = null;
        $theSystem = null;

        try {
            $ice = \Ice\Initialize();
            $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
            $theSystem = \model\TheSystemPrxHelper::checkedCast($proxy);

            // Elimination of person
            $vehiculoBackend = $theSystem->eliminarVehiculo($patente);

            // The rut not exist in database
            if ($vehiculoBackend == null) {
                return redirect()->back()->with('alert', 'Vehiculo No Encontrado!');
            } //TODO: Verificar backend no responde

            if ($ice) {
                $ice->destroy();
            }

            // Elimination completed
            return redirect()->back()->with('success', 'Vehiculo Eliminado Correctamente!');

        } catch (Exception $ex) {
            echo $ex;
        }
    }
}
