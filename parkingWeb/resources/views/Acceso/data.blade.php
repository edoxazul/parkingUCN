 <table>
    <thead>
        <tr>
            <th>Id</th>
            <th>Fecha y hora</th>
            <th>Patente</th>
        </tr>
    </thead>
    <tbody>
        @foreach($access as $row)
            <tr>
                <td>{{$row->uid}} </td>
                <td>{{$row->horaEntrada}} </td>
                <td>{{$row->patente}}</td>
            </tr>
        @endforeach
    </tbody>
</table>

