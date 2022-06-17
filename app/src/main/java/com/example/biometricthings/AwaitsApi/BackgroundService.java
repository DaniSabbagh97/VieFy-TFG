package com.example.biometricthings.AwaitsApi;

import android.app.IntentService;
import android.content.Intent;

import com.example.biometricthings.model.User;

import androidx.annotation.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundService extends IntentService {
    /**
     * Dani
     */
    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        User u = new User();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/api/users/profile")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();



    }
}
