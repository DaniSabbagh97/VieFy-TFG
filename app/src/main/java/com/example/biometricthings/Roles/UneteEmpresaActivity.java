package com.example.biometricthings.Roles;

import androidx.annotation.NonNull;
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

import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Empresaa;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class UneteEmpresaActivity extends AppCompatActivity {

    private ArrayList<Empresaa> ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unete_empresa);

        /*RecyclerView mRecycler = (RecyclerView) this.findViewById(R.id.rvEmpresas);

        init3();*/

        String token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Empresaa>> p = apiService.obtenerEmpresas(token);

        p.enqueue(new Callback<ArrayList<Empresaa>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Empresaa>> call, @NonNull Response<ArrayList<Empresaa>> response) {


                ee = response.body();
                System.out.println(ee);

               /* ListAdapterEmpresa listAdapterEmpresa = new ListAdapterEmpresa(ee, UneteEmpresaActivity.this);//FIXME CREAR OTRO ADAPTER O BUSCAR LA MANERA DE METERLO AQUI PERO PARECE IMPOSIBLEEE
                RecyclerView recyclerView = findViewById(R.id.rvEmpresas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(UneteEmpresaActivity.this));
                recyclerView.setAdapter(listAdapterEmpresa);*/

                ListAdapterEmpresa listAdapterEmpresa = new ListAdapterEmpresa(ee, UneteEmpresaActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rvEmpresas);
                LinearLayoutManager manager = new LinearLayoutManager(UneteEmpresaActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(listAdapterEmpresa);

                     /* for (Propiedades propiedades : pp) {

                          System.out.println(propiedades.getNombre().toString());



                      }*/

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Empresaa>> call, @NonNull Throwable t) {

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(t.getMessage());

            }
        });




    }




    public void init3(){





    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }


    }