package com.example.biometricthings.Roles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.Profesor.PrevisualizarPracticaActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Solicitud;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MostrarEmpresaIndividualActivity extends AppCompatActivity {

    private TextView tvTituloEmpresa;
    private TextView tvSloganEmpresa;
    private TextView tvAnuncioEmpresa;
    private TextView tvInformacion;
    private TextView tvAdjunto;
    private EditText etMensaje;
    private Button btnAdjuntarPDF;
    private Button btnEnviarSolicitud;
    private Button btnPrevisualizar;

    private int SELECT_CV = 200;
    private int idUser;

    private PasarBytes pb;


    private String nombre, slogan, anuncio, nombrePDF, mensaje, token;
    private int id;
    private Solicitud solicitud;
    private byte[] pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_empresa_individual);

        tvTituloEmpresa = (TextView) findViewById(R.id.tvTituloEmpresa);
        tvSloganEmpresa = (TextView) findViewById(R.id.tvSloganEmpresa);
        tvAnuncioEmpresa = (TextView) findViewById(R.id.tvAnuncioEmpresa);
        tvInformacion = (TextView) findViewById(R.id.tvInformacion);
        tvAdjunto = (TextView) findViewById(R.id.tvAdjunto);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        btnAdjuntarPDF = (Button) findViewById(R.id.btnAdjuntarPDF);
        btnEnviarSolicitud = (Button) findViewById(R.id.btnEnviarSolicitud);
        btnPrevisualizar = (Button) findViewById(R.id.btnPrevisualizar);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt("id");
            nombre = extras.getString("nombre");
            slogan = extras.getString("slogan");
            anuncio = extras.getString("anuncio");

            tvTituloEmpresa.setText(nombre);
            tvSloganEmpresa.setText(slogan);
            tvAnuncioEmpresa.setText(anuncio);

        }

        token = cargarPreferencias();
        btnAdjuntarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(MostrarEmpresaIndividualActivity.this)){
                    openImageChooser();

                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) MostrarEmpresaIndividualActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }


            }
        });

        btnPrevisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb = new PasarBytes(pdf);

                Intent i = new Intent(view.getContext(), PrevisualizarPracticaActivity.class);
                i.putExtra("pb",pb);
                view.getContext().startActivity(i);

            }
        });

        btnEnviarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje = etMensaje.getText().toString().trim();
                solicitud = new Solicitud(id, idUser, mensaje, pdf);


                final APIService apiService = RetroClass.getAPIService();
                Call<Boolean> upDocument = apiService.addPdf(solicitud, token);
                upDocument.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        System.out.println("OOOOOOOOOOOOOOOO");
                        System.out.println(response.body());

                        if(response.body()){
                            Intent i = new Intent(MostrarEmpresaIndividualActivity.this, HomeActivity.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_CV) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    try {

                        pdf = save(selectedUri);
                        Toast.makeText(this, "PDF Seleccionado Correctamente", Toast.LENGTH_SHORT).show();
                        tvAdjunto.setText("Archivo Adjuntado: "+nombrePDF);
                        btnPrevisualizar.setEnabled(true);



                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        //super.onActivityResult(requestCode, resultCode, data);
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona tú curriculum"), SELECT_CV);

    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");
        idUser = preferences.getInt("idUser", 0);

        return tokenFinal;


    }

    private byte[] save(Uri selectedUri) throws IOException {

            InputStream iStream = getContentResolver().openInputStream(selectedUri);
            byte[] inputData = Utils.getBytes(iStream);
            nombrePDF = getFileName(selectedUri);

            /**/

            return inputData;

    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}