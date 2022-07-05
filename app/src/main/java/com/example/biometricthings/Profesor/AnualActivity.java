package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Antales;
import com.example.biometricthings.model.Anual;
import com.example.biometricthings.model.Trimestral;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class AnualActivity extends AppCompatActivity {

    private EditText etMensajeHaciendaAnual;
    private EditText etCuentaPersonal;
    private EditText etBeneficioAnual;

    private Button btnEnviarAnual;

    private String mensajeAnual;
    private String token;
    private int cuentaPersonal;
    private int beneficioAnual;
    private Anual anual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anual);

        etMensajeHaciendaAnual = (EditText) findViewById(R.id.etMensajeHaciendaAnual);
        etCuentaPersonal = (EditText) findViewById(R.id.etCuentaPersonal);
        etBeneficioAnual = (EditText) findViewById(R.id.etBeneficioAnual);

        btnEnviarAnual = (Button) findViewById(R.id.btnEnviarAnual);

        token = cargarPreferencias();

        btnEnviarAnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mensajeAnual = etMensajeHaciendaAnual.getText().toString().trim();
                cuentaPersonal = Integer.parseInt(etCuentaPersonal.getText().toString().trim());
                beneficioAnual = Integer.parseInt(etBeneficioAnual.getText().toString().trim());

                anual = new Anual(mensajeAnual, cuentaPersonal, beneficioAnual, "Anual");

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> anl = apiService.crearAntales(anual, token);

                anl.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if(response.body()){
                            Intent i = new Intent(AnualActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }
                });


            }
        });


    }
    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}