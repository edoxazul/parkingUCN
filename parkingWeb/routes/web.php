<?php

use Illuminate\Support\Facades\Route;
require_once 'Ice.php';
require '..\..\domain.php';

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

    
    $ic = null;
        try
        {
            $ic = Ice\initialize();
            $proxy = $ic->stringToProxy("Test:default -p 4000");
            $connection = model\TheSystemPrxHelper::checkedCast($proxy);
            if(!$connection)
            {
                throw new RuntimeException("Invalid proxy");
            }
            
        }
        catch(Exception $ex)
        {
            echo $ex;
        }

        if($ic)
        {
            $ic->destroy(); // Clean up
        }

    return view('welcome');
});
