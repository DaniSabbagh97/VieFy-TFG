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
import com.example.biometricthings.Profesor.HaciendaActivity;
import com.example.biometricthings.Profesor.ListAdapterClases;
import com.example.biometricthings.Profesor.SeleccionaClaseActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class ListaPracticasDisponibles extends AppCompatActivity {

    private ArrayList<Practicas> pp;
    private String token;
    private Button btnAtrasP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_practicas_disponibles);
        token = cargarPreferencias();

        btnAtrasP = (Button) findViewById(R.id.btnAtrasP);

        btnAtrasP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListaPracticasDisponibles.this, HomeActivity.class);
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

            Call<ArrayList<Practicas>> p = apiService.obtenerPracticas(token);

        p.enqueue(new Callback<ArrayList<Practicas>>() {
            @Override
            public void onResponse(Call<ArrayList<Practicas>> call, Response<ArrayList<Practicas>> response) {

                if(response.code()==200){
                    if(response.body()!=null){
                        pp = response.body();

                        System.out.println(pp);
                        System.out.println("PUUUUUUUUUUUU54UUUUUU6");

                        ListAdapterPracticas listAdapterPracticas = new ListAdapterPracticas(pp, ListaPracticasDisponibles.this);
                        RecyclerView recyclerView = findViewById(R.id.rvPracticas);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ListaPracticasDisponibles.this));
                        recyclerView.setAdapter(listAdapterPracticas);

                    }else{
                        Toast.makeText(ListaPracticasDisponibles.this, "Intentelo más tarde...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ListaPracticasDisponibles.this, "El servidor envío una respuesta de: "+response.code()+" ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Practicas>> call, Throwable t) {
                System.out.println("PUUUUUUUUUUUUUUUUUU");
                System.out.println(t.getMessage());
            }
        });

    }

}