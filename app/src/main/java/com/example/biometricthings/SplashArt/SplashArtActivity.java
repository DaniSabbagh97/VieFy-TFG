package com.example.biometricthings.SplashArt;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.biometricthings.BiometricsActivity;
import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Profesor.SeleccionaClaseActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.Timer;
import java.util.TimerTask;

public class SplashArtActivity extends AppCompatActivity {

    private String tokenFinal;
    private String token;
    LottieAnimationView lottie;
    private ImageView iv;
    private User user;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_art);

        iv = findViewById(R.id.iv);
        lottie = findViewById(R.id.lottie);

        lottie.animate().translationY(-2000).setDuration(2000).setStartDelay(2500);

        iv.animate().translationY(1400).setDuration(2000).setStartDelay(2500);
        //iv.animate().translationY(3000).setDuration(2700).setStartDelay(0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!token.equals("NO")) {
                    /*;*/
                    final APIService apiService = RetroClass.getAPIService();

                    user = new User();

                    Call<User> u = apiService.userProfile(token);

                    u.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.body()!=null){
                                user = response.body();
                                rol = user.getRol();
                                if(rol.equals("Profesor")){
                                    Intent i = new Intent(SplashArtActivity.this, SeleccionaClaseActivity.class);//LogInActivity
                                    startActivity(i);
                                    finish();

                                }else{
                                    Intent i = new Intent(SplashArtActivity.this, HomeActivity.class);//LogInActivity
                                    startActivity(i);
                                    finish();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }else{
                    Intent i = new Intent(SplashArtActivity.this, LogInActivity.class);
                    startActivity(i);
                    finish();
                }




            }
        },3700);

        token = cargarPreferencias();

      /*  TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if(!token.equals("NO")) {
                    Intent i = new Intent(SplashArtActivity.this, HomeActivity.class);//LogInActivity
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
        t.schedule(tarea, 1000);*/
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        tokenFinal = preferences.getString("token","NO");

        return tokenFinal;


    }
}