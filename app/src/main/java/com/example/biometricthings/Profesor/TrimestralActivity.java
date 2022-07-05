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
import android.widget.TextView;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Trimestral;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class TrimestralActivity extends AppCompatActivity {

    private EditText etMensajeHacienda;
    private EditText etCuentaEmpresa;
    private EditText etBeneficioTrimestral;

    private Button btnEnviarTrimestral;

    private String mensaje;
    private String token;
    private int cuentaEmpresa;
    private int beneficioTrimestral;
    private Trimestral trimestral;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimestral);

        etMensajeHacienda = (EditText) findViewById(R.id.etMensajeHacienda);
        etCuentaEmpresa = (EditText) findViewById(R.id.etCuentaEmpresa);
        etBeneficioTrimestral = (EditText) findViewById(R.id.etBeneficioTrimestral);

        btnEnviarTrimestral = (Button) findViewById(R.id.btnEnviarTrimestral);

        token = cargarPreferencias();


        btnEnviarTrimestral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mensaje = etMensajeHacienda.getText().toString().trim();
                cuentaEmpresa = Integer.parseInt(etCuentaEmpresa.getText().toString().trim());
                beneficioTrimestral = Integer.parseInt(etBeneficioTrimestral.getText().toString().trim());

                trimestral = new Trimestral(mensaje, cuentaEmpresa, beneficioTrimestral, "Trimestral");

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> anl = apiService.crearAntales2(trimestral, token);

                anl.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if(response.body()){
                            Intent i = new Intent(TrimestralActivity.this, HomeActivity.class);
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