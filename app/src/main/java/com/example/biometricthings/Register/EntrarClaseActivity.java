package com.example.biometricthings.Register;

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

                Call<Boolean> p = apiService.putIdClase(c, token);

                p.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.body()){//FIXME ARREGLAR SI FALLA AL PONER EL CÓDIGO
                            Intent i = new Intent(EntrarClaseActivity.this, LogInActivity.class);
                            i.putExtra("mail", mail);
                            startActivity(i);
                            finish();

                        }else{
                            Toast.makeText(EntrarClaseActivity.this, "La clave es incorrecta", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(EntrarClaseActivity.this, LogInActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

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