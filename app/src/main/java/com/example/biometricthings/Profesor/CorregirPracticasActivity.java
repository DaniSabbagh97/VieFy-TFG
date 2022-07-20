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
import com.example.biometricthings.Roles.ListAdaptarPracticasCompradas;
import com.example.biometricthings.Roles.ListAdapterPracticasCorregir;
import com.example.biometricthings.Roles.ListaPracticasCompradas;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.idClase;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class CorregirPracticasActivity extends AppCompatActivity {

    private ArrayList<Compras> cc;
    private String token;
    private Button btnAtrasPC;
    private int clase;
    private idClase id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corregir_practicas);


        btnAtrasPC = (Button) findViewById(R.id.btnAtrasPC);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            clase = extras.getInt("idClase");
        }

        token = cargarPreferencias();
        btnAtrasPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CorregirPracticasActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        init(clase);
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

    public void init(int idc){

        id = new idClase(idc);

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Compras>> c = apiService.obtenerPracticasEntregadas(id, token);

        c.enqueue(new Callback<ArrayList<Compras>>() {
            @Override
            public void onResponse(Call<ArrayList<Compras>> call, Response<ArrayList<Compras>> response) {

                if(response.code()==200){
                    if(response.body()!=null){
                        cc = response.body();

                        System.out.println(cc);
                        System.out.println("PUUUUUUUUU54UUUUUUUUU6");

                        ListAdapterPracticasCorregir listAdapterPracticasCorregir = new ListAdapterPracticasCorregir(cc, CorregirPracticasActivity.this);
                        RecyclerView recyclerView = findViewById(R.id.rvPracticasCorregir);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CorregirPracticasActivity.this));
                        recyclerView.setAdapter(listAdapterPracticasCorregir);
                    }else{
                        Toast.makeText(CorregirPracticasActivity.this, "Intentelo más tarde...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CorregirPracticasActivity.this, "El servidor envío una respuesta de: "+response.code()+" ", Toast.LENGTH_SHORT).show();
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