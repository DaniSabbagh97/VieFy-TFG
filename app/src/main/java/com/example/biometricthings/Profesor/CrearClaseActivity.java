package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biometricthings.R;
import com.example.biometricthings.Utils.DatePickerFragment;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.sql.Date;
import java.util.ArrayList;

public class CrearClaseActivity extends AppCompatActivity {
    private EditText etNombreClase;
    private EditText etNumAlumnos;
    private EditText etClaveAcceso;
    private EditText etFechaInicio;
    private EditText etFechaFin;
    private Button btnCrear;

    private String nombreClase, claveAcceso, fechaInicio, fechaFin, token;

    private int numAlumnos;

    private Clase c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_clase);

        token = cargarPreferencias();

        etNombreClase = (EditText) findViewById(R.id.etNombreClase);
        etNumAlumnos = (EditText) findViewById(R.id.etNumAlumnos);
        etClaveAcceso = (EditText) findViewById(R.id.etClaveAcceso);
        etFechaInicio = (EditText) findViewById(R.id.etFechaInicio);
        etFechaFin = (EditText) findViewById(R.id.etFechaFin);
        btnCrear = (Button) findViewById(R.id.btnCrear);

        etFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etFechaInicio:
                        showDatePickerDialog1();
                        break;
                }
            }
        });

        etFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etFechaFin:
                        showDatePickerDialog2();
                        break;
                }
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombreClase = etNombreClase.getText().toString().trim();
                numAlumnos = Integer.parseInt(etNumAlumnos.getText().toString().trim());
                claveAcceso = etClaveAcceso.getText().toString().trim();
                fechaInicio = (etFechaInicio.getText().toString().trim());
                fechaFin = (etFechaFin.getText().toString().trim());

                c = new Clase(claveAcceso, nombreClase, numAlumnos, fechaInicio, fechaFin);

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> p = apiService.crearClases(c,token);

                p.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.body()){
                            Intent i = new Intent(CrearClaseActivity.this, SeleccionaClaseActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(CrearClaseActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    private void showDatePickerDialog1() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
               etFechaInicio.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    private void showDatePickerDialog2() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFechaFin.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

        public String cargarPreferencias(){
            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            String tokenFinal = preferences.getString("token","No existe el token");

            return tokenFinal;


        }
}

