package com.example.biometricthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.biometricthings.Profesor.SeleccionaClaseActivity;
import com.example.biometricthings.Propiedades.PropiedadesActivity;
import com.example.biometricthings.PruebasLoeches.MainActivity;
import com.example.biometricthings.Register.EntrarClaseActivity;
import com.example.biometricthings.Register.RegisterActivity;
import com.example.biometricthings.Test.TestActivity;
import com.example.biometricthings.model.JWTToken;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.example.biometricthings.tokenmanager.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private User user, u;

    private EditText etUserMail,etUserPassword;
    private Button senddata;
    private Button btnRv;
    private TextView tvRegistro;
    private TokenManager tokenManager;

    private String mail;
    private int idUser;

    private boolean test;
    private boolean active = false;


    private String TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            mail = extras.getString("mail");
        }


        etUserMail = (EditText) findViewById(R.id.etUserMail);
        etUserPassword = (EditText) findViewById(R.id.etUserPassword);
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);
        SpannableString content = new SpannableString("¿No tienes Cuenta?. ¡Registrate Aquí!");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvRegistro.setText(content);
        senddata = (Button) findViewById(R.id.senddata);
        btnRv = (Button) findViewById(R.id.btnRv);
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println(mail);
        etUserMail.setText(mail);

        tokenManager = new TokenManager(getApplicationContext());

        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this, RegisterActivity.class);
                i.putExtra("mail", mail);
                startActivity(i);

            }
        });

        btnRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usernameval=etUserMail.getText().toString();
                final String userpassval = etUserPassword.getText().toString();

                final APIService apiService = RetroClass.getAPIService();

                user = new User(usernameval,userpassval);
                Call<JWTToken> jwtTokenCall = apiService.userlogin(user);

                jwtTokenCall.enqueue(new Callback<JWTToken>() {
                    @Override
                    public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {

                        JWTToken jwtToken = response.body();

                        tokenManager.createLoginSession(usernameval,jwtToken.getToken());
                        TOKEN = jwtToken.token;
                        u = jwtToken.dbUser;
                        guardarPreferencias(TOKEN);

                        int activo = u.getIsActive();
                        int propiedad = u.getId_propiedades();
                        int idClase = u.getId_clase();
                        String rol = u.getRol();
                        idUser = u.getId_user();

                        /*System.out.println(TOKEN);

                        System.out.println("VVVVVVVVVVVVVVVVV");
                        System.out.println(u.getIsActive());
                        System.out.println("VVVVVVVVVVVVVVVVV");*/

                    if(rol.equals("Profesor")){
                        Intent i = new Intent(LogInActivity.this, SeleccionaClaseActivity.class);
                        startActivity(i);
                    }else if(rol.equals("Alumno")) {
                        if(idClase==0){
                            Intent i = new Intent(LogInActivity.this, EntrarClaseActivity.class);
                            i.putExtra("mail", mail);
                            startActivity(i);
                            finish();

                        }else{
                            if (activo == 1 && propiedad != 0) {
                                Intent i = new Intent(LogInActivity.this, HomeActivity.class);//TODO COMPROBAR QUE HAYA HECHO O NO EL TEST
                                startActivity(i);
                                finish();

                            } else if (activo == 0) {
                                Intent i = new Intent(LogInActivity.this, TestActivity.class);//TODO Ya hizo el test
                                startActivity(i);
                                finish();

                            } else if (propiedad == 0) {
                                Intent i = new Intent(LogInActivity.this, PropiedadesActivity.class);//TODO Ya hizo el test
                                startActivity(i);
                                finish();

                            } else {
                                Intent i = new Intent(LogInActivity.this, BiometricsActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }


                    }
                        
                }

                    @Override
                    public void onFailure(Call<JWTToken> call, Throwable t) {

                        showToast("POlla"+t.getMessage());
                    }
                });
            }
        });
    }

    void showToast(String msg)
    {
        Toast.makeText(this, ""+msg, Toast.LENGTH_LONG).show();
        System.out.println(msg);
    }

    public void guardarPreferencias(String token){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.putInt("idUser",idUser);
        editor.commit();

    }

 /*   public boolean ComprobarTest(String t) throws IOException {

        u = new User();
        final APIService apiService = RetroClass.getAPIService();
        Call<User> isActiveCall = apiService.userProfile(t);

        Response<User> usr = isActiveCall.execute();

        test = u.isActive();





                /*(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                u = response.body();

                test = u.isActive();
                System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
                System.out.println(test);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        return test;
    }*/


}
