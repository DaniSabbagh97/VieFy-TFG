package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.biometricthings.Propiedades.ListAdapter;
import com.example.biometricthings.Propiedades.PropiedadesActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class SeleccionaClaseActivity extends AppCompatActivity {

    private ArrayList<Clase> cc;
    private String token;

    private Button btnCrearClase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_clase);

        token = cargarPreferencias();
        btnCrearClase = (Button) findViewById(R.id.btnCrearClase);

        btnCrearClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeleccionaClaseActivity.this, CrearClaseActivity.class);
                startActivity(i);
                finish();
            }
        });

        init();
    }

    public void init(){

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Clase>> p = apiService.obtenerClases(token);

        p.enqueue(new Callback<ArrayList<Clase>>() {
            @Override
            public void onResponse(Call<ArrayList<Clase>> call, Response<ArrayList<Clase>> response) {
                cc = response.body();

                System.out.println(cc);
                System.out.println("PUUUUUUUUUUUUUUUUUU6");

                ListAdapterClases listAdapterClases = new ListAdapterClases(cc, SeleccionaClaseActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rvClases);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(SeleccionaClaseActivity.this));
                recyclerView.setAdapter(listAdapterClases);

            }

            @Override
            public void onFailure(Call<ArrayList<Clase>> call, Throwable t) {
                System.out.println("PUUUUUUUUUUUUUUUUUU");
                System.out.println(t.getMessage());
            }
        });

    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}