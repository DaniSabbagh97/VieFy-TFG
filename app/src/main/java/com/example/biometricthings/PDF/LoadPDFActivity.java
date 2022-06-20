package com.example.biometricthings.PDF;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.R;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LoadPDFActivity extends AppCompatActivity {
     private Button btnLoadPDF;
     private String token;
    private int SELECT_ANYFILE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pdfactivity);

        token = cargarPreferencias();

        btnLoadPDF = (Button) findViewById(R.id.btnLoadPDF);

        btnLoadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(LoadPDFActivity.this)){
                    openImageChooser();
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) LoadPDFActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Elige una imagen"), SELECT_ANYFILE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_ANYFILE) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    if (true) {
                        saveinDB(selectedUri);
                        Toast.makeText(this, "Imagen Guardada "+selectedUri, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }


        //super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean saveinDB(Uri selectedUri) {
        try{
            InputStream iStream = getContentResolver().openInputStream(selectedUri);
            byte[] inputData = Utils.getBytes(iStream);

            final APIService apiService = RetroClass.getAPIService();
            Call<Boolean> upDocument = apiService.subirPDF(inputData, token);
            upDocument.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    System.out.println("OOOOOOOOOOOOOOOO");
                    System.out.println(response.body());

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });

            return true;
        }catch (Exception e){
            return false;
        }
    }

        private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}