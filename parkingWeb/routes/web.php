<?php
/*
 * MIT License
 *
 * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

use App\Http\Controllers\DataExportController;
use Illuminate\Support\Facades\Route;
require_once 'Ice.php';

//for fixed dir from domain.php
require_once __DIR__."/../../domain.php";
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    //Zero Ice
    $ice = null;
    $theSystem = null;

    try {
        $ice = \Ice\Initialize();
        $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
        $theSystem = \model\TheSystemPrxHelper::checkedCast($proxy);

        $access = $theSystem->getAllAccess();


        if ($ice) {
            $ice->destroy();
        }

        return view('welcome', [
            'access' => $access
        ]);
    }
    catch(Exception $e) {
        return view('welcome');
    }
});

// Route for testing connection with backend
Route::get('/test', 'ConnectionController@connectionTest');

// Route for Persona

Route::get('/persona','PersonaController@index');

Route::get('/ingresarPersona','PersonaController@ingresarView');
Route::post('/ingresarPersona','PersonaController@insertar');

Route::get('/eliminarPersona','PersonaController@eliminarView');
Route::post('/eliminarPersona','PersonaController@eliminar');

Route::get('/editarPersona','PersonaController@editarIndex');
Route::post('/editarP','PersonaController@editar');
Route::post('/editarPersonaPost','PersonaController@editarPost');

// Route for Vehiculo
Route::get('/vehiculo','VehiculoController@index');

Route::get('/ingresarVehiculo','VehiculoController@ingresarView');
Route::post('/ingresarVehiculo','VehiculoController@insertar');

Route::get('/eliminarVehiculo','VehiculoController@eliminarView');
Route::post('/eliminarVehiculo','VehiculoController@eliminar');

Route::get('/editarVehiculo','VehiculoController@editarIndex');
Route::post('/editarV','VehiculoController@editar');
Route::post('/editarVehiculoPost','VehiculoController@editarPost');

//  Route for export data
Route::get('/exportar', function() {
    return (new DataExportController)->download('data.xlsx');
});

