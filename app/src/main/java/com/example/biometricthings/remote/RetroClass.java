package com.example.biometricthings.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetroClass {


//private static final String BASEURL="http://192.168.132.2";
private static final String BASEURL="http://192.168.1.10:8080/api/auth/";
private static Retrofit getRetrofitInstance()
    {

        Gson gson = new GsonBuilder().setLenient().create();


        return new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create(gson)).build();


    }

    public static APIService getAPIService()
    {
        return getRetrofitInstance().create(APIService.class);
    }


}
