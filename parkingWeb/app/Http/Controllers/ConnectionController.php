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

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use http\Url;
require_once 'Ice.php';


class ConnectionController extends Controller
{
    public function connectionTest(){

        try
        {   // Ice connection
            $ice = \Ice\Initialize();
            $proxy = $ice->stringToProxy("TheSystem:default -p 4020");
            $connection = \model\TheSystemPrxHelper::checkedCast($proxy);
            if(!$connection)
            {
                echo "Error in connection";
                throw new RuntimeException("Invalid proxy");
            }

            $client_time = (int) round(microtime(true)*1000);
            echo("Client time: ".$client_time);

            // Calls system method
            $delay = $connection->getDelay($client_time);
            echo "<br>";
            echo("Delay: ".$delay);

        }
        catch(Exception $ex)
        {
            echo $ex;
        }

        if($ice)
        {
            $ice->destroy();
        }
    }
}
