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
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Antales;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class HaciendaActivity extends AppCompatActivity {

    private ArrayList<Antales> aa;
    private String token;
    private int idClase;
    private Clase cls;

    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacienda);

        btnAtras = (Button) findViewById(R.id.btnAtras);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            idClase = extras.getInt("idClase");

        }

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HaciendaActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });




        token = cargarPreferencias();

        init(idClase);


    }

    public void init(int id){

        cls = new Clase(id);

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Antales>> antales = apiService.obtenerAntales(cls,token);



        antales.enqueue(new Callback<ArrayList<Antales>>() {
            @Override
            public void onResponse(Call<ArrayList<Antales>> call, Response<ArrayList<Antales>> response) {
                if(response.body()!=null){
                    aa = response.body();
               

                System.out.println(aa);
                System.out.println("PUUUUUUUUUUUUUUUUUU6");

                ListAdapterAntales listAdapterAntales = new ListAdapterAntales(aa, HaciendaActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rvAntales);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(HaciendaActivity.this));
                recyclerView.setAdapter(listAdapterAntales);

                }else{
                    Toast.makeText(HaciendaActivity.this, "Aún no hay Trimestrales ni Anuales", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Antales>> call, Throwable t) {

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