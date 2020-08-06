<?php

use Illuminate\Support\Facades\Route;
require_once 'Ice.php';
require_once '../../domain.php';
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

    return view('welcome');
});

Route::get('/test',function (){
    $ic = null;
    try
    {
        $ic = Ice\initialize();
        $proxy = $ic->stringToProxy("TheSystem:default -p 4020");
        $connection = model\TheSystemPrxHelper::checkedCast($proxy);
        if(!$connection)
        {
            echo "Error in connection";
            throw new RuntimeException("Invalid proxy");
        }
        echo "Connection completed";
        echo "<br>";

        $client_time = (int) round(microtime(true)*1000);
        echo("Client time: ".$client_time);

        // Calls interface method
        $delay = $connection->getDelay($client_time);
        echo "<br>";
        echo("Delay: ".$delay);
        


    }
    catch(Exception $ex)
    {
        echo $ex;
    }

    if($ic)
    {
        $ic->destroy(); // Clean up
    }
});
