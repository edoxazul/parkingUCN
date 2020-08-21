 <table>
    <thead>
        <tr>
            <th>Id</th>
            <th>Fecha y hora</th>
            <th>Patente</th>
            <th>Portería</th>
        </tr>
    </thead>
    <tbody>
        @foreach($access as $row)
            <tr>
                <td>{{$row->uid}} </td>
                <td>{{$row->horaEntrada}} </td>
                <td>{{$row->patente}}</td>
                <td>@if($row->porteria == 0)
                        Sur
                    @elseif($row->porteria ==1)
                        Mancilla
                    @elseif($row->porteria==2)
                        Sangra
                    @endif</td>
            </tr>
        @endforeach
    </tbody>
</table>

