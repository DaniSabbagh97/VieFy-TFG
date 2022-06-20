package com.example.biometricthings.PDF;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class ReadPDFActivity extends AppCompatActivity {

    private Empresa e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdfactivity);

        //e = new Empresa();
        PDFView pdfView = findViewById(R.id.pdfView);

        String token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<Empresa> p = apiService.obtenerPdf(token);

        p.enqueue(new Callback<Empresa>() {
                      @Override
                      public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                          e = response.body();
                          System.out.println("LLEGO AQUIIIIIIIIII");
                          byte[] pdf = e.getPdfString();

                          pdfView.fromBytes(pdf).load();
                      }

                      @Override
                      public void onFailure(Call<Empresa> call, Throwable t) {
                          System.out.println("AITORPUTA");
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