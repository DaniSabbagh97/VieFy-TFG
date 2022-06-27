package com.example.biometricthings.Profesor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.R;
import com.example.biometricthings.Utils.DatePickerFragment;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.InputStream;

public class SubidaPracticas extends AppCompatActivity {

    private TextView tvArchivo;
    private EditText etNombrePractica;
    private EditText etValorPractica;
    private EditText etCantidadEjercicios;
    private EditText etFechaEntrega;
    private EditText etBeneficio;

    private Button btnAdjuntarPDF;
    private Button btnPrevisualizacion;
    private Button btnSubirPractica;
    private Button btnVolver;

    private byte[] inputData;
    private PasarBytes pb;

    private Context context;
    private int SELECT_FILE = 200;
    private String token;
    private String nombrePDF;

    private String nombrePractica, fechaEntrega;
    private int valorEuros, beneficio, numEjercicios, idClase;

    private Practicas p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subida_practicas);
        token = cargarPreferencias();
        tvArchivo = (TextView) findViewById(R.id.tvArchivo);
        etNombrePractica = (EditText) findViewById(R.id.etNombrePractica);
        etValorPractica = (EditText) findViewById(R.id.etValorPractica);
        etCantidadEjercicios = (EditText) findViewById(R.id.etCantidadEjercicios);
        etFechaEntrega = (EditText) findViewById(R.id.etFechaEntrega);
        etBeneficio = (EditText) findViewById(R.id.etBeneficio);

        btnAdjuntarPDF = (Button) findViewById(R.id.btnAdjuntarPDF);
        btnPrevisualizacion = (Button) findViewById(R.id.btnPrevisualizacion);
        btnPrevisualizacion.setEnabled(false);
        btnSubirPractica = (Button) findViewById(R.id.btnSubirPractica);
        btnVolver = (Button) findViewById(R.id.btnVolver);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){

            idClase = extras.getInt("idClase");

        }


        btnAdjuntarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(SubidaPracticas.this)){
                    openImageChooser();
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) SubidaPracticas.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }

            }
        });

        etFechaEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.etFechaEntrega:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        btnPrevisualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb = new PasarBytes(inputData);

                Intent i = new Intent(SubidaPracticas.this, PrevisualizarPracticaActivity.class);
                i.putExtra("pb",pb);
                startActivity(i);

            }
        });

        btnSubirPractica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TODO COMPROBAR Y VALIDAR CAMPOS
                nombrePractica = etNombrePractica.getText().toString().trim();
                valorEuros = Integer.parseInt(etValorPractica.getText().toString().trim());
                numEjercicios = Integer.parseInt(etCantidadEjercicios.getText().toString().trim());
                fechaEntrega = etFechaEntrega.getText().toString().trim();
                beneficio = Integer.parseInt(etBeneficio.getText().toString().trim());

                p = new Practicas(nombrePractica, numEjercicios, valorEuros, fechaEntrega, idClase, inputData, beneficio);

                final APIService apiService = RetroClass.getAPIService();
                Call<Boolean> practica = apiService.subirPractica(p, token);
                practica.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.body()){
                            borradoDatos();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                        System.out.println(t.getMessage());

                    }
                });

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SubidaPracticas.this, HomeActivity.class);
                i.putExtra("idClase", idClase);
                startActivity(i);
            }
        });

    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escoge un archivo PDF"), SELECT_FILE);

    }

    public void borradoDatos(){

        tvArchivo.setText("Adjuntado: ");
        etNombrePractica.setText("");
        etValorPractica.setText("");
        etCantidadEjercicios.setText("");
        etFechaEntrega.setText("");
        etBeneficio.setText("");
        btnPrevisualizacion.setEnabled(false);

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
                        tvArchivo.setText("Adjuntado: "+nombrePDF);
                        btnPrevisualizacion.setEnabled(true);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
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

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFechaEntrega.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }


    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");



        return tokenFinal;


    }
}