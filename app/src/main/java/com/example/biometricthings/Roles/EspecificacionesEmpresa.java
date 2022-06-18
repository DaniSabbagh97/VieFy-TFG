package com.example.biometricthings.Roles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Propiedades.MostrarPropiedadActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.HistoricoCuentaParticulares;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class EspecificacionesEmpresa extends AppCompatActivity {
    private EditText etNombreEmpresa;
    private EditText etSlogan;
    private EditText etTituloAnuncio;
    private EditText etCuerpoAnuncio;
    private Button btnCrearEmpresa;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private int idProp;

    private Empresa empresa;
    private HistoricoCuentaParticulares hcp;
    private String token;

    private String nombreEmpresa;
    private String slogan;
    private String tituloAnuncio;
    private String fraseAnuncio;
    private int saldo;
    private int saldoEmpresario;
    private int idUser;
    private double precio;
    private TextView tvInfo;
    private Button btnSi;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especificaciones_empresa);
        hcp = new HistoricoCuentaParticulares();
        token = cargarPreferencias();
        Bundle extras = getIntent().getExtras();
        idProp = extras.getInt("idProp");
        precio = extras.getDouble("precio");


        etNombreEmpresa = (EditText) findViewById(R.id.etNombreEmpresa);
        etSlogan = (EditText) findViewById(R.id.etSlogan);
        etTituloAnuncio = (EditText) findViewById(R.id.etTituloAnuncio);
        etCuerpoAnuncio = (EditText) findViewById(R.id.etCuerpoAnuncio);
        btnCrearEmpresa = (Button) findViewById(R.id.btnCrearEmpresa);

        btnCrearEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final APIService apiService = RetroClass.getAPIService();
                Call<HistoricoCuentaParticulares> getSaldo = apiService.obtenerSaldo(token);
                getSaldo.enqueue(new Callback<HistoricoCuentaParticulares>() {
                    @Override
                    public void onResponse(Call<HistoricoCuentaParticulares> call, Response<HistoricoCuentaParticulares> response) {
                        hcp = response.body();
                        saldo = hcp.getSaldo();
                        idUser = hcp.getId_user();
                        saldoEmpresario = (saldo *10);
                        nombreEmpresa = etNombreEmpresa.getText().toString().trim();
                        slogan = etSlogan.getText().toString().trim();
                        tituloAnuncio = etTituloAnuncio.getText().toString().trim();
                        fraseAnuncio = etCuerpoAnuncio.getText().toString().trim();

                        empresa = new Empresa(nombreEmpresa, idUser, saldoEmpresario, saldoEmpresario, precio, idProp, slogan, tituloAnuncio, fraseAnuncio);
                        if(response.body()!=null){
                            createNewDialog(empresa);
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoricoCuentaParticulares> call, Throwable t) {

                    }
                });



            }
        });



    }
    public void createNewDialog(Empresa e){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.popup_confirmacion_propiedad, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);
        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("¿Estás seguro de querer crear una empresa con estos datos?");

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final APIService apiService = RetroClass.getAPIService();
                    Call<Boolean> registroProp = apiService.insertarEmpresa(e, token);
                    registroProp.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.body()){
                                Intent i = new Intent(EspecificacionesEmpresa.this, HomeActivity.class);
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

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }


}