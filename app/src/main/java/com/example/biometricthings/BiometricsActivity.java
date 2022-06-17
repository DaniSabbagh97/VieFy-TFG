package com.example.biometricthings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biometricthings.Notificacion.NotificacionActivity;
import com.example.biometricthings.Propiedades.PropiedadesActivity;
import com.example.biometricthings.Register.RegisterActivity;
import com.example.biometricthings.RetrofitThings.PreLogInANDSingUpActivity;
import com.example.biometricthings.Test.TestActivity;

import java.util.concurrent.Executor;


public class BiometricsActivity extends AppCompatActivity {

    private Button btnAuth;
    private TextView tvAuthStatus;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private Button btnJWT;
    private Button btnTest;
    private Button btnPreLogSign;
    private Button btnRegistro;
    private Button btnNotificacion;
    private Button btnPropiedades;

    private String tokenFinal;
    private String tokenFinal2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometrics);

        btnAuth=findViewById(R.id.btnAuth);
        tvAuthStatus=findViewById(R.id.tvAuthStatus);

        btnJWT = findViewById(R.id.btnJWT);
        btnTest = findViewById(R.id.btnTest);
        btnPreLogSign = findViewById(R.id.btnPreLogSign);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnNotificacion = findViewById(R.id.btnNotificacion);
        btnPropiedades = findViewById(R.id.btnPropiedades);

        btnPropiedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, PropiedadesActivity.class);
                startActivity(i);
            }
        });

        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, NotificacionActivity.class);
                startActivity(i);
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnPreLogSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, PreLogInANDSingUpActivity.class);
                startActivity(i);
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, TestActivity.class);
                startActivity(i);
            }
        });

        btnJWT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BiometricsActivity.this, LogInActivity.class);
                startActivity(i);
            }
        });
    //FIXME SI TENGO UN JWT EN LA APP ACCEDO AL INICIO DE SESION BIOMETRICO
        //FIXME SI NO AL LOGIN
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt=new androidx.biometric.BiometricPrompt(BiometricsActivity.this, executor,new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString){
               super.onAuthenticationError(errorCode, errString);
               tvAuthStatus.setText("Error: "+errString);
           }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result ){
               super.onAuthenticationSucceeded(result);
               tvAuthStatus.setText("Success");

               String tokenFinal2 = cargarPreferencias();
               if(tokenFinal2 != null){
                   Intent i = new Intent(BiometricsActivity.this, HomeActivity.class);
                   startActivity(i);
               }else{
                   Toast.makeText(BiometricsActivity.this, "Ventana Log In", Toast.LENGTH_SHORT).show();
               }

               //TODO RECOGER EL TOKEN DE SHARED PREFERENCES


           }
            @Override
            public void onAuthenticationFailed(){
               super.onAuthenticationFailed();
               tvAuthStatus.setText("Authentication Failed");
           }

        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Auth").setSubtitle("Login using fingerprint or face").setNegativeButtonText("Cancel").build();





        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                biometricPrompt.authenticate(promptInfo);

            }
        });
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}