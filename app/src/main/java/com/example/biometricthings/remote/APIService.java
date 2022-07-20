package com.example.biometricthings.remote;

import com.example.biometricthings.model.Antales;
import com.example.biometricthings.model.Anual;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.Correcion;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Empresaa;
import com.example.biometricthings.model.HistoricoCuentaParticulares;
import com.example.biometricthings.model.JWTToken;
import com.example.biometricthings.model.Multa;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.Solicitud;
import com.example.biometricthings.model.SolicitudAceptada;
import com.example.biometricthings.model.Test;
import com.example.biometricthings.model.TestResult;
import com.example.biometricthings.model.Trimestral;
import com.example.biometricthings.model.User;
import com.example.biometricthings.model.idClase;


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

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/pdf", hasBody = true)
 Call<Boolean> subirPDF(@Body byte[] b, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/empresas/getPdf")
 Call<Empresa> obtenerPdf(@Header("Authorization") String token);

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/empresas/obtenerEmpresa")
 Call<ArrayList<Empresaa>> obtenerEmpresas(@Header("Authorization") String token);

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/solicitudes/addPdf", hasBody = true)
 Call<Boolean> addPdf(@Body Solicitud s, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "DELETE", path = "http://192.168.1.10:8080/api/users/borrar", hasBody = true)
 Call<Boolean> borrar(@Body User u);

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/clases/getClases")
 Call<ArrayList<Clase>> obtenerClases(@Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/clases/crearClases", hasBody = true)
 Call<Boolean> crearClases(@Body Clase c, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/putIdclase", hasBody = true)
 Call<Clase> putIdClase(@Body Clase cls, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/getListUsers", hasBody = true)
 Call<ArrayList<User>> getListaUsers(@Body Clase c, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/clases/updateUsos", hasBody = true)
 Call<Boolean> updateUsos(@Body Clase cl, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/practicas/subirPractica", hasBody = true)
 Call<Boolean> subirPractica(@Body Practicas p, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/solicitudes/getListaSolicitudes")
 Call<Empresa> getListaSolicitudes(@Header("Authorization") String token);//TODO CON TOKEN


 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/solicitudes/contratar", hasBody = true)
 Call<Boolean> contratarAsalariado(@Body SolicitudAceptada sa, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/solicitudes/borrarSolicitud", hasBody = true)
 Call<Boolean> borrarSolicitud(@Body SolicitudAceptada sa, @Header("Authorization") String token);//TODO CON TOKEN


 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/users/getSalario")
 Call<User> getSalario(@Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/setSalario", hasBody = true)
 Call<Boolean> setSalario(@Body User usr, @Header("Authorization") String token);//TODO CON TOKEN




 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/antales/get", hasBody = true)
 Call<ArrayList<Antales>> obtenerAntales(@Body Clase c, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/antales/", hasBody = true)
 Call<Boolean> crearAntales(@Body Anual an, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/antales/", hasBody = true)
 Call<Boolean> crearAntales2(@Body Trimestral tri, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/practicas/")
 Call<ArrayList<Practicas>> obtenerPracticas(@Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "GET", path = "http://192.168.1.10:8080/api/compras/")
 Call<ArrayList<Compras>> obtenerPracticasCompradas(@Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/multas", hasBody = true)
 Call<Boolean> multa(@Body Multa m, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/compras", hasBody = true)
 Call<Boolean> crearCompra(@Body Practicas p, @Header("Authorization") String token);//TODO CON TOKEN

 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/users/getImagenPerfil", hasBody = true)
 Call<User> getImagenPerfil(@Body User u);


 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/compras/corregir", hasBody = true)
 Call<Boolean> corregirPractica(@Body Correcion c, @Header("Authorization") String token);//TODO CON TOKEN


/**
 *
 *
 *
 *
 *
 *
 *TODO compra.entrega para obtener la practica entregada
 *TODO compra.practica.pdf para obtener el pdf de la practica
 *
 *
 *
 *
 *
 *
**/
//TODO AQUI SE OBTIENEN LAS ENTREGAS DE UNA CLASE PARA QUE EL PROFESOR LAS CORRIJA
@HTTP(method = "POST", path = "http://192.168.1.10:8080/api/compras/entregas", hasBody = true)
Call<ArrayList<Compras>> obtenerPracticasEntregadas(@Body idClase id, @Header("Authorization") String token);//TODO CON TOKEN


//TODO AQUI SE ENTREGA LA PRACTICA
 @HTTP(method = "POST", path = "http://192.168.1.10:8080/api/compras/entrega", hasBody = true)
 Call<Boolean> entregarPractica(@Body Practicas p, @Header("Authorization") String token);//TODO CON TOKEN


//TODO TIENE QUE BUSCAR EL ID DE LAS EMPRESAS QUE ESTÉN EN SU CLASE

//todo tiene que buscar los usuarios que tengan el mismo id_clase que él y sean empresarios
}
