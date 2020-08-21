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
import cl.ucn.disc.pdis.parkingapp.R;
import cl.ucn.disc.pdis.parkingapp.repository.Communicator;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Acceso;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Persona;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Porteria;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ServerException;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.Vehiculo;
import es.dmoral.toasty.Toasty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AuthorizeActivity extends AppCompatActivity {

    /*
    La persona
     */
    Persona persona;

    /*
    El vehiculo
     */
    Vehiculo vehiculo;

    /*
    El comunicador con el backend.
     */
    Communicator communicator = new Communicator();

    /*
    Los opciones de entrada representados en botones.
     */
    RadioButton r1, r2, r3;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);

        this.setTitle("Registrar Acceso");

        Intent i = getIntent();

        persona = (Persona) i.getSerializableExtra("persona");
        vehiculo = (Vehiculo) i.getSerializableExtra("vehiculo");

        r1 = findViewById(R.id.opcion_0);
        r2 = findViewById(R.id.opcion_1);
        r3 = findViewById(R.id.opcion_2);

        TextView place = findViewById(R.id.place_1);
        place.setText(vehiculo.marca);

        place = findViewById(R.id.place_2);
        place.setText(vehiculo.modelo);

        place = findViewById(R.id.place_3);
        place.setText(String.valueOf(vehiculo.anio));

        place = findViewById(R.id.place_4);
        place.setText(vehiculo.patente);

        place = findViewById(R.id.place_5);
        place.setText(vehiculo.observaciones);

        place = findViewById(R.id.place_6);
        place.setText(persona.nombre);

        place = findViewById(R.id.place_7);
        place.setText(persona.run);

        place = findViewById(R.id.place_8);

        switch (persona.sexo.value()){
            case 0:
                place.setText("VARON");
                break;
            case 1:
                place.setText("MUJER");
                break;
            case 2:
                place.setText("OTRO");
                break;
        }

        place = findViewById(R.id.place_9);

        switch (persona.categoriaPersona.value()){
            case 0:
                place.setText("FUNCIONARIO");
                break;
            case 1:
                place.setText("ACADEMICO");
                break;
            case 2:
                place.setText("ESTUDIANTE");
                break;
        }

    }

    public void onClickBtn(View v)
    {

        Porteria porteria;

        if ( r1.isChecked()) {

            porteria = Porteria.SUR;

        } else if ( r2.isChecked()) {

            porteria = Porteria.MANCILLA;

        } else if ( r3.isChecked()) {

            porteria = Porteria.SANGRA;

        }else{

            Toasty.info(this, "Seleccione una entrada", Toast.LENGTH_SHORT, true).show();
            return;
        }

        // FIXME: Generar la conexion fuera del hilo de la UI
        try {

           Acceso acceso = communicator.autorizarVehiculo(vehiculo.patente,porteria);
           Toasty.success(this, "Success: "+ acceso.horaEntrada, Toast.LENGTH_SHORT, true).show();

        } catch (ServerException exception) {

            Toasty.error(this, "Server error 500", Toast.LENGTH_SHORT, true).show();
        } catch (Exception e){

            Toasty.error(this, "App Error", Toast.LENGTH_SHORT, true).show();
        } finally {
            finish();
        }

        finish();
    }




}