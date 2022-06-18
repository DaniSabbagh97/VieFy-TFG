package com.example.biometricthings.Test;

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

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Propiedades.PropiedadesActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Test;
import com.example.biometricthings.model.TestResult;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class ConfirmacionTestActivity extends AppCompatActivity {

    private Button btnSi;
    private Button btnNo;
    private int conPropiedad;
    private int salarioObtained;
    private int salarioEmpresa;
    private int salarioAutonomo;

    private User u;
    private TestResult tr;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_test);

        btnSi = (Button) findViewById(R.id.btnSi);
        btnNo = (Button) findViewById(R.id.btnNo);

        Test t = getIntent().getParcelableExtra("test");
        salarioObtained = getIntent().getIntExtra("salario", 0);

        token = cargarPreferencias();








        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final APIService apiS = RetroClass.getAPIService();
                System.out.println("UUUUUUUUUUUUUUUUUUUUU");
                System.out.println(t.getImpB2());
                System.out.println(t.getImpB3());
                System.out.println(t.getImpB4());
                System.out.println(t.getImpB5());
                System.out.println(t.getFutC2());
                System.out.println(t.getFutC3());
                System.out.println(t.getFutC4());
                System.out.println(t.getFutC5());
                System.out.println(t.getImpB8());
                System.out.println(t.getImpB9());
                System.out.println(t.getImpB10());
                System.out.println(t.getImpB11());
                System.out.println(t.getRol());
                System.out.println(t.getPareja());
                System.out.println(t.getNHijos());
               /* salarioEmpresa = salarioObtained*2000;
                salarioAutonomo = salarioObtained*200;
                u = new User(t.getId_user(), salarioAutonomo, salarioEmpresa);*/
               // tr = new TestResult(t,u);
                u = new User();
                Call<User> registroTest = apiS.registerTest(t,token);//TODO HACER isActive a true si se registra el test
                registroTest.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response!=null){
                            System.out.println(response);
                            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYY");
                            System.out.println(conPropiedad);
                            u = response.body();
                            conPropiedad = u.getId_propiedades();
                            if(conPropiedad!=0){
                                Intent i = new Intent(ConfirmacionTestActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            }else if(conPropiedad==0){
                                Intent i = new Intent(ConfirmacionTestActivity.this, PropiedadesActivity.class);
                                i.putExtra("key", 1);
                                startActivity(i);
                                finish();                            }

                        }else{
                            conPropiedad = 0;
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("TTTTTTTTTTTTTTTTTT");
                        System.out.println(conPropiedad);

                    }
                });

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmacionTestActivity.this, TestActivity.class);
                startActivity(i);
            }
        });





    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}