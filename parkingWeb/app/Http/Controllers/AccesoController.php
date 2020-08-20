<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Exports\DataExportController;
use Maatwebsite\Excel\Facades\Excel;

class AccesoController extends Controller
{
    public function export(){
        return Excel::download(new DataExportController, 'data.xlsx');
    }
}