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
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.InputStream;
import java.util.ArrayList;

public class EntregaPracticasActivity extends AppCompatActivity {

    private EditText etNombrePractica;
    private EditText etMensajeAlProfe;

    private Spinner spNombrePractica;
    private ArrayList<String> arrPracticas;
    private ArrayList<Practicas> pp;

    private TextView tvNombreArchivo;
    private String nombrePractica, mensajePractica;

    private Button btnEnviarPractica;
    private Button btnAdjuntarPDF;
    private byte[] inputData;
    private String token;
    private String nombrePDF;

    private int SELECT_FILE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_practicas);

        etNombrePractica = (EditText) findViewById(R.id.etNombrePractica);
        etMensajeAlProfe = (EditText) findViewById(R.id.etMensajeAlProfe);

        spNombrePractica = (Spinner) findViewById(R.id.spNombrePractica);

        tvNombreArchivo = (TextView) findViewById(R.id.tvNombreArchivo);

        btnEnviarPractica = (Button) findViewById(R.id.btnEnviarPractica);
        btnAdjuntarPDF = (Button) findViewById(R.id.btnAdjuntarPDF);
        arrPracticas = new ArrayList<>();
        pp = new ArrayList<>();
        /*String[] plants = new String[]{
                "Select an item...",
                "California sycamore",
                "Mountain mahogany",
                "Butterfly weed",
                "Carrot weed"
        };*/




        token = cargarPreferencias();

//TODO SACAR DE LA BBDD TODAS LAS PRÁCTICAS DEL ID CLASE QUE HAYA EN EL TOKEN Y METERLAS EN arrPracticas
        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<Practicas>> p = apiService.obtenerPracticas(token);



        p.enqueue(new Callback<ArrayList<Practicas>>() {
            @Override
            public void onResponse(Call<ArrayList<Practicas>> call, Response<ArrayList<Practicas>> response) {
                arrPracticas.clear();
                arrPracticas.add("Selecciona la Práctica");
                pp = response.body();
                System.out.println("uuuu");
                System.out.println(pp);
                for (Practicas p : pp){
                    arrPracticas.add(p.getNombrePractica());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(EntregaPracticasActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrPracticas){
                    @Override
                    public boolean isEnabled(int position){
                        if(position == 0)
                        {

                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if(position == 0){

                            tv.setTextColor(Color.GRAY);
                        }
                        else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spNombrePractica.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<ArrayList<Practicas>> call, Throwable t) {

                System.out.println(t.getMessage());

            }
        });







        btnAdjuntarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(EntregaPracticasActivity.this)){
                    openImageChooser();
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) EntregaPracticasActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }
            }
        });

        btnEnviarPractica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombrePractica = etNombrePractica.getText().toString().trim();
                mensajePractica = etMensajeAlProfe.getText().toString().trim();


                //TODO ENVIAR A LA BBDD LA PRÁCTICA PARA QUE LA CORRIJA EL PROFESOR


            }
        });

    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escoge un archivo PDF"), SELECT_FILE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    try{
                        InputStream iStream = getContentResolver().openInputStream(selectedUri);
                        inputData = Utils.getBytes(iStream);
                        nombrePDF = getFileName(selectedUri);
                        tvNombreArchivo.setText("Adjuntado: "+nombrePDF);
                       // btnPrevisualizacion.setEnabled(true);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

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