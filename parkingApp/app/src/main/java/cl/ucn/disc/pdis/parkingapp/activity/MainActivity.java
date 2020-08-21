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
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Location;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;

    ArrayList<Vehiculo> vehiculos;

    Communicator communicator = new Communicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.barra_busqueda_patente);
        listView = findViewById(R.id.lista_vehiculos);

        try {
            Vehiculo[] vehiculo2 = communicator.obtenerVehiculos();

            vehiculos = new ArrayList<>(Arrays.asList(vehiculo2));
        } catch (ServerException exception) {
            exception.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(this,R.layout.row, this.vehiculos);

        listView.setAdapter(adapter);



    }

    /**
     * La clase Adapter
     */
    class MyAdapter extends BaseAdapter {

        Context context;
        private int layout;
        ArrayList<Vehiculo> vehiculos;

        /**
         * Constructor del adapter
         * @param c Contexto del activity
         * @param layout cantidad del filas.
         * @param vehiculos Lista de vehiculos
         */
        MyAdapter(Context c, int layout, ArrayList<Vehiculo> vehiculos){

            this.context = c;
            this.vehiculos = vehiculos;
            this.layout = layout;

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

            v= layoutInflater.inflate(R.layout.row, null);

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