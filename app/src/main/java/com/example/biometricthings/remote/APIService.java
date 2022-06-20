package com.example.biometricthings.remote;

import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.HistoricoCuentaParticulares;
import com.example.biometricthings.model.JWTToken;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.Test;
import com.example.biometricthings.model.TestResult;
import com.example.biometricthings.model.User;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface APIService {

 //@FormUrlEncoded
 @POST("http://192.168.1.10:8080/api/auth")
 Call<JWTToken> userlogin(@Body User user);

 /*@GET("http://192.168.1.10:8080/api/auth")
 Call<String> getUser(@Header("Authorization") String authorization);*/

 @GET("http://192.168.1.10:8080/api/users/profile")
 Call<User> userProfile(@Header("Authorization") String token);

 @GET("http://192.168.1.10:8080/api/users/profile/test")
 Call<User> userProfileTest(@Header("Authorization") String token);

 @GET("http://192.168.1.10:8080/api/historicoCuentaParticulares/saldo")
 Call<HistoricoCuentaParticulares> obtenerSaldo(@Header("Authorization") String token);

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/register", hasBody = true)
 Call<Integer> registerUser(@Body User user);

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/test/registerTest", hasBody = true)
 Call<User> registerTest(@Body Test t, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/registrarPropiedad", hasBody = true)
 Call<String> registroPropiedad(@Body Propiedades p, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/propiedades/obtener")
 Call<ArrayList<Propiedades>> obtenerPropiedades(@Header("Authorization") String token);

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/propiedades/obtenerLocal")
 Call<ArrayList<Propiedades>> obtenerLocales(@Header("Authorization") String token);

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/empresas/insertarEmpresa", hasBody = true)
 Call<Boolean> insertarEmpresa(@Body Empresa e, @Header("Authorization") String token);
//todo https://www.youtube.com/watch?v=hCz_aHnt3j8

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/empresas/pdf", hasBody = true)
 Call<Boolean> subirPDF(@Body byte[] b, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/empresas/getPdf")
 Call<Empresa> obtenerPdf(@Header("Authorization") String token);

}