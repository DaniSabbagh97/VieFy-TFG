package com.example.biometricthings.Register;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class EntrarClaseActivity extends AppCompatActivity {

    private EditText etClave;
    private Button btnSiguiente;

    private String clave, token, mail;
    private Clase c;
    private Clase c2;
    private Clase c3;
    private int usos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_clase);
        Intent i = getIntent();
        mail = i.getStringExtra("mail");
        token = cargarPreferencias();
        System.out.println("TOKEEEEEEEEN");
        System.out.println(token);
        etClave = (EditText) findViewById(R.id.etClaveClase);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                clave = etClave.getText().toString().trim();
                c = new Clase(clave);

                final APIService apiService = RetroClass.getAPIService();

                Call<Clase> p = apiService.putIdClase(c, token);

                p.enqueue(new Callback<Clase>() {
                    @Override
                    public void onResponse(Call<Clase> call, @NonNull Response<Clase> response) {
                        if(response.body()!=null) {
                            c2 = response.body();
                            usos = c2.getNumero_de_usos();
                            if (usos <= 0) {

                                Toast.makeText(EntrarClaseActivity.this, "No queda hueco en esta clase", Toast.LENGTH_SHORT).show();
                                Toast.makeText(EntrarClaseActivity.this, "Contacta con tu profesor", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EntrarClaseActivity.this, LogInActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                usos = usos - 1;
                                c3 = new Clase(c2.getId_clase(), usos);
                                final APIService apiService = RetroClass.getAPIService();

                                Call<Boolean> s = apiService.updateUsos(c3, token);

                                s.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, @NonNull Response<Boolean> response) {
                                        if(response.body()){
                                            Intent i = new Intent(EntrarClaseActivity.this, LogInActivity.class);
                                            i.putExtra("mail", mail);
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
                            }else{
                                Toast.makeText(EntrarClaseActivity.this, "La clave es incorrecta", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EntrarClaseActivity.this, LogInActivity.class);
                                startActivity(i);
                                finish();
                            }

                    }

                    @Override
                    public void onFailure(Call<Clase> call, Throwable t) {

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