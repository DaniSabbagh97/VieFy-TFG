package com.example.biometricthings.SplashArt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.biometricthings.BiometricsActivity;
import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashArtActivity extends AppCompatActivity {

    private String tokenFinal;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_art);

        token = cargarPreferencias();

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if(!token.equals("NO")) {
                    Intent i = new Intent(SplashArtActivity.this, BiometricsActivity.class);//LogInActivity
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashArtActivity.this, LogInActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        Timer t = new Timer();
        t.schedule(tarea, 1000);
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        tokenFinal = preferences.getString("token","NO");

        return tokenFinal;


    }
}