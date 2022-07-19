package com.example.biometricthings.Roles;

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
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class ListaPracticasCompradas extends AppCompatActivity {

    private ArrayList<Compras> cc;
    private String token;
    private Button btnAtrasC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_practicas_compradas);

        btnAtrasC = (Button) findViewById(R.id.btnAtrasC);

        token = cargarPreferencias();

        btnAtrasC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaPracticasCompradas.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        init();
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

    public void init(){

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Compras>> c = apiService.obtenerPracticasCompradas(token);

        c.enqueue(new Callback<ArrayList<Compras>>() {
            @Override
            public void onResponse(Call<ArrayList<Compras>> call, Response<ArrayList<Compras>> response) {

                if(response.code()==200){
                    if(response.body()!=null){
                        cc = response.body();

                        System.out.println(cc);
                        System.out.println("PUUUUUUUUUUUUUUUUUU6");

                        ListAdaptarPracticasCompradas listAdaptarPracticasCompradas = new ListAdaptarPracticasCompradas(cc, ListaPracticasCompradas.this);
                        RecyclerView recyclerView = findViewById(R.id.rvCompras);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ListaPracticasCompradas.this));
                        recyclerView.setAdapter(listAdaptarPracticasCompradas);
                    }else{
                        Toast.makeText(ListaPracticasCompradas.this, "Intentelo más tarde...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ListaPracticasCompradas.this, "El servidor envío una respuesta de: "+response.code()+" ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Compras>> call, Throwable t) {
                System.out.println("PUUUUUUUUUUUUUUUUUU");
                System.out.println(t.getMessage());
            }
        });

    }
}