/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package cl.ucn.disc.pdis.parkingapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import cl.ucn.disc.pdis.parkingapp.R;
import cl.ucn.disc.pdis.parkingapp.repository.Communicator;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Persona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;
import es.dmoral.toasty.Toasty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;

    /*
    Lista de vehiculos del backend.
     */
    ArrayList<Vehiculo> vehiculos;

    /*
    Lista de personas del backend.
     */
    ArrayList<Persona> personas;

    /*
    El comunicador con el backend.
     */
    Communicator communicator = new Communicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.barra_busqueda_patente);
        listView = findViewById(R.id.lista_vehiculos);

        this.setTitle("Parking App");

        // Conexion con el backend para obtener los vehiculos y las personas.
        // FIXME: Generar la conexion fuera del hilo de la UI
        try {

            Vehiculo[] vehiculosBD = communicator.obtenerVehiculos();
            vehiculos = new ArrayList<>(Arrays.asList(vehiculosBD));

            Persona[] personasBD = communicator.obtenerPersonas();
            personas = new ArrayList<>(Arrays.asList(personasBD));

        } catch (ServerException exception) {

            exception.printStackTrace();
            Toasty.error(this, "Server error 500", Toast.LENGTH_SHORT, true).show();

        } catch (Exception e){

            e.printStackTrace();
            Toasty.error(this, "App Error", Toast.LENGTH_SHORT, true).show();

        }

        // Inicializar el adaptador con la lista de vehiculos.
        MyAdapter adapter = new MyAdapter(this,this.vehiculos);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(vehiculos.get(position));
            }
        });

        listView.setAdapter(adapter);

        //TODO: Configurar boton para sincronizar la lista de vehiculos con la BD del backend.

    }


    private void selectItem(Vehiculo vehiculo){

        Intent intent = new Intent(this, AuthorizeActivity.class);

        intent.putExtra("vehiculo",vehiculo);
        intent.putExtra("persona",findPersona(vehiculo.runDuenio));

        startActivity(intent);

    }

    private Persona findPersona(String run){

        for (Persona p : personas) {
            if (p.run.equals(run)) {
                return p;
            }
        }
        return new Persona();
    }

    /**
     * La clase Adapter
     */
    static class MyAdapter extends BaseAdapter {

        Context context;
        ArrayList<Vehiculo> vehiculos;

        /**
         * Constructor del adaptador del ListView.
         *
         * @param c         Contexto del activity
         * @param vehiculos Lista de vehiculos
         */
        MyAdapter(Context c, ArrayList<Vehiculo> vehiculos) {

            this.context = c;
            this.vehiculos = vehiculos;

        }

        @Override
        public int getCount() {
            return this.vehiculos.size();
        }

        @Override
        public Object getItem(int position) {
            return this.vehiculos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View v = convertView;

            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            v = layoutInflater.inflate(R.layout.row, null);

            //Se obtienen los textView del row para ser llenados
            TextView patente = v.findViewById(R.id.item_patente);
            TextView marca = v.findViewById(R.id.item_marca);
            TextView modelo = v.findViewById(R.id.item_modelo);
            TextView anio = v.findViewById(R.id.item_anio);

            // Estos son llenados con los datos del vehiculo.
            patente.setText(vehiculos.get(position).patente);
            marca.setText(vehiculos.get(position).marca);
            modelo.setText(vehiculos.get(position).modelo);
            anio.setText(String.valueOf(vehiculos.get(position).anio));


            return v;
        }
    }


}