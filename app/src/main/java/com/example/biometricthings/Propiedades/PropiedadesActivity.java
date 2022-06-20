package com.example.biometricthings.Propiedades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;
import java.util.List;

public class PropiedadesActivity extends AppCompatActivity {

    List<Propiedades> elements;
    private TextView tvPropiedades;
    private ArrayList<Propiedades> pp;
    private ArrayList<Empresa> ee;
    private String nombre;
    private double precio;
    private int habitaciones;
    int f;
    String nombreEmpresa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propiedades);
        tvPropiedades = (TextView) findViewById(R.id.tvPropiedades);
        Bundle extras = getIntent().getExtras();
        f = extras.getInt("key");
        if(extras.getString("empresa")!=null){
            nombreEmpresa = extras.getString("empresa");
        }
        if(f==1){
            init();
            tvPropiedades.setText("Seleccione una vivienda: ");
        }else if(f==2){
            init2();
            tvPropiedades.setText("Seleccione un local: ");
        }else if(f==3){
            init3();
            tvPropiedades.setText("Solicitar trabajo en: ");
        }


    }

    public void init(){

        String token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Propiedades>> p = apiService.obtenerPropiedades(token);

        p.enqueue(new Callback<ArrayList<Propiedades>>() {

              @Override
              public void onResponse(@NonNull Call<ArrayList<Propiedades>> call, @NonNull Response<ArrayList<Propiedades>> response) {


                  pp = response.body();

                  ListAdapter listAdapter = new ListAdapter(pp, PropiedadesActivity.this);
                  RecyclerView recyclerView = findViewById(R.id.rvPropiedades);
                  recyclerView.setHasFixedSize(true);
                  recyclerView.setLayoutManager(new LinearLayoutManager(PropiedadesActivity.this));
                  recyclerView.setAdapter(listAdapter);

                     /* for (Propiedades propiedades : pp) {

                          System.out.println(propiedades.getNombre().toString());



                      }*/

              }

              @Override
              public void onFailure(@NonNull Call<ArrayList<Propiedades>> call, @NonNull Throwable t) {

                  System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                  System.out.println(t.getMessage());

              }
          });







}


    public void init2(){

        String token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Propiedades>> p = apiService.obtenerLocales(token);

        p.enqueue(new Callback<ArrayList<Propiedades>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Propiedades>> call, @NonNull Response<ArrayList<Propiedades>> response) {


                pp = response.body();

                ListAdapter listAdapter = new ListAdapter(pp, PropiedadesActivity.this);
                RecyclerView recyclerView = findViewById(R.id.rvPropiedades);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PropiedadesActivity.this));
                recyclerView.setAdapter(listAdapter);

                     /* for (Propiedades propiedades : pp) {

                          System.out.println(propiedades.getNombre().toString());



                      }*/

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Propiedades>> call, @NonNull Throwable t) {

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(t.getMessage());

            }
        });







    }

    public void init3(){

        String token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Empresa>> p = apiService.obtenerEmpresas(token);

        p.enqueue(new Callback<ArrayList<Empresa>>() {

            @Override
            public void onResponse(@NonNull Call<ArrayList<Empresa>> call, @NonNull Response<ArrayList<Empresa>> response) {


                ee = response.body();

               /* ListAdapter listAdapter = new ListAdapter(ee, PropiedadesActivity.this);//FIXME CREAR OTRO ADAPTER O BUSCAR LA MANERA DE METERLO AQUI PERO PARECE IMPOSIBLEEE
                RecyclerView recyclerView = findViewById(R.id.rvPropiedades);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PropiedadesActivity.this));
                recyclerView.setAdapter(listAdapter);*/

                     /* for (Propiedades propiedades : pp) {

                          System.out.println(propiedades.getNombre().toString());



                      }*/

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Empresa>> call, @NonNull Throwable t) {

                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
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