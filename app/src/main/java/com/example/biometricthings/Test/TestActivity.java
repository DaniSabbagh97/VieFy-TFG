package com.example.biometricthings.Test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.biometricthings.R;
import com.example.biometricthings.model.Test;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.LinkedList;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;


public class TestActivity extends AppCompatActivity {

    private EditText etB2;
    private EditText etB3;
    private EditText etB4;
    private EditText etB5;

    private EditText etC2;
    private EditText etC3;
    private EditText etC4;
    private EditText etC5;

    private EditText etB8;
    private EditText etB9;
    private EditText etB10;
    private EditText etB11;

    private RadioButton rbEmpresario;
    private RadioButton rbAsalariado;
    private RadioButton rbAutonomo;

    private RadioButton rbSolteroPareja;
    private RadioButton rbSolteroSinPareja;
    private RadioButton rbCasado;

    private RadioButton rbSi;
    private RadioButton rbNo;

    private Button btnEnviar;

    private int b2,b3,b4,b5,c2,c3,c4,c5,b8,b9,b10,b11;
    private int totalPreg1, totalPreg2, totalPreg3;
    private String situacionLaboral, estadoCivil;
    private double felicidadInicial, felicidadFinal;
    private boolean hijos;
    private boolean flag1 = false;
    private boolean flag2 = false;
    private boolean flag3 = false;
    private boolean flag4 = false;



    private int numHijos;

    private int idUserObtained;

    public int expediente;
    private int idUser;
    private int expedienteFinal;
    private int salario;

    private ProgressDialog mDialog;


    private Test t;
    private User user, us;


    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent i = getIntent();
        idUserObtained = i.getIntExtra("idUserObtained",0);


        String tokenGuardado = cargarPreferencias();
        System.out.println("00000000000000000000000000000");
        System.out.println(tokenGuardado);
        obtenerUser(tokenGuardado);
        
        /*mDialog = new ProgressDialog(this);
        mDialog.setMessage("Obteniendo Información. . .");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        obtenerUser();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obtenerUser();
        mDialog.dismiss( );*/


            /*CompletableFuture.supplyAsync(() -> obtenerUser())
                    .thenAccept(integer -> mDialog.dismiss());*/


        //

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(expediente);
        etB2 = (EditText) findViewById(R.id.etB2);
        etB3 = (EditText) findViewById(R.id.etB3);
        etB4 = (EditText) findViewById(R.id.etB4);
        etB5 = (EditText) findViewById(R.id.etB5);

        etC2 = (EditText) findViewById(R.id.etC2);
        etC3 = (EditText) findViewById(R.id.etC3);
        etC4 = (EditText) findViewById(R.id.etC4);
        etC5 = (EditText) findViewById(R.id.etC5);

        etB8 = (EditText) findViewById(R.id.etB8);
        etB9 = (EditText) findViewById(R.id.etB9);
        etB10 = (EditText) findViewById(R.id.etB10);
        etB11 = (EditText) findViewById(R.id.etB11);

        rbEmpresario = (RadioButton) findViewById(R.id.rbEmpresario);
        rbAsalariado = (RadioButton) findViewById(R.id.rbAsalariado);
        rbAutonomo = (RadioButton) findViewById(R.id.rbAutonomo);


        rbSolteroPareja = (RadioButton) findViewById(R.id.rbSolteroPareja);
        rbSolteroSinPareja = (RadioButton) findViewById(R.id.rbSolteroSinPareja);
        rbCasado = (RadioButton) findViewById(R.id.rbCasado);

        rbSi = (RadioButton) findViewById(R.id.rbSi);
        rbNo = (RadioButton) findViewById(R.id.rbNo);

        btnEnviar = (Button) findViewById(R.id.btnEnviar);


        btnEnviar.setEnabled(false);

        btnEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                System.out.println(expediente);


                if(etB2.getText().toString().equals(etB3.getText().toString()) ||
                        etB3.getText().toString().equals(etB4.getText().toString()) ||
                        etB4.getText().toString().equals(etB5.getText().toString()) ||
                        etB2.getText().toString().equals(etB3.getText().toString())){
                    Toast.makeText(TestActivity.this, "No se pueden repetir valores en la pregunta 1", Toast.LENGTH_SHORT).show();

                }else{
                    if(TextUtils.isEmpty(etB2.getText().toString()) ||
                            TextUtils.isEmpty(etB3.getText().toString()) ||
                            TextUtils.isEmpty(etB4.getText().toString()) ||
                            TextUtils.isEmpty(etB5.getText().toString()) ||
                            TextUtils.isEmpty(etB8.getText().toString()) ||
                            TextUtils.isEmpty(etB9.getText().toString()) ||
                            TextUtils.isEmpty(etB10.getText().toString()) ||
                            TextUtils.isEmpty(etB11.getText().toString()) ||
                            TextUtils.isEmpty(etC2.getText().toString()) ||
                            TextUtils.isEmpty(etC3.getText().toString()) ||
                            TextUtils.isEmpty(etC4.getText().toString()) ||
                            TextUtils.isEmpty(etC5.getText().toString())){
                        Toast.makeText(TestActivity.this, "No puede haber campos Vacios", Toast.LENGTH_SHORT).show();

                    }else{
                        b2 =  Integer. parseInt(etB2.getText().toString());
                        b3 =  Integer. parseInt(etB3.getText().toString());
                        b4 =  Integer. parseInt(etB4.getText().toString());
                        b5 =  Integer. parseInt(etB5.getText().toString());

                        c2 =  Integer. parseInt(etC2.getText().toString());
                        c3 =  Integer. parseInt(etC3.getText().toString());
                        c4 =  Integer. parseInt(etC4.getText().toString());
                        c5 =  Integer. parseInt(etC5.getText().toString());

                        b8 =  Integer. parseInt(etB8.getText().toString());
                        b9 =  Integer. parseInt(etB9.getText().toString());
                        b10 =  Integer. parseInt(etB10.getText().toString());
                        b11 =  Integer. parseInt(etB11.getText().toString());
                    }
                }

                totalPreg1 = b2+b3+b4+b5;

                totalPreg2 = c2+c3+c4+c5;

                totalPreg3 = b8+b9+b10+b11;


                if(totalPreg1!=10 || totalPreg2!=10 || totalPreg3!=10){
                    Toast.makeText(TestActivity.this, "Debe rellenar correctamente las preguntas", Toast.LENGTH_SHORT).show();
                    flag1 = false;
                }else{
                    flag1 = true;
                   if(rbEmpresario.isChecked()){
                       situacionLaboral = "Empresario";
                       flag2 = true;
                   }else if(rbAsalariado.isChecked()){
                       situacionLaboral = "Asalariado";
                       flag2 = true;
                   }else if(rbAutonomo.isChecked()){
                       situacionLaboral = "Autonomo";
                       flag2 = true;
                   }else{
                       flag2 = false;
                       Toast.makeText(TestActivity.this, "Debe seleccionar una situación laboral", Toast.LENGTH_SHORT).show();
                   }

                    if(rbSolteroPareja.isChecked()){
                        estadoCivil = "conPareja";
                        flag3 = true;
                    }else if(rbSolteroSinPareja.isChecked()){
                        estadoCivil = "sinPareja";
                        flag3 = true;
                    }else if(rbCasado.isChecked()){
                        estadoCivil = "Casado";
                        flag3 = true;
                    }else{
                        flag3 = false;
                        Toast.makeText(TestActivity.this, "Debe seleccionar un estado civil", Toast.LENGTH_SHORT).show();
                    }

                    if(rbSi.isChecked()){
                        hijos = true;
                        flag4 = true;

                    }else if(rbNo.isChecked()){
                        hijos = false;
                        flag4 = true;
                    }else{
                        flag4 = false;
                        Toast.makeText(TestActivity.this, "Debe seleccionar si tendrás hijos", Toast.LENGTH_SHORT).show();
                    }
                    if(flag1 && flag2 && flag3 && flag4){
                        if(hijos){
                            numHijos = (int) Math.floor(Math.random()*3+1);

                            System.out.println("HIJOSS");
                            System.out.println(numHijos);

                        }else{
                            numHijos = 0;
                        }
                        System.out.println("Los datos son: "+totalPreg1+" "+totalPreg2+" "+totalPreg3+" "+situacionLaboral+" "+estadoCivil+" "+hijos);
                            //TODO CONSEGUIR EL EXPEDIENTE PARA LOS CALCULOS DE FELICIDAD

                        LinkedList<Integer> stack = new LinkedList<Integer>();
                        while(expediente > 0){
                            stack.push(expediente % 10);
                            expediente = expediente / 10;
                        }
                        while ((!stack.isEmpty())){
                            int num = stack.pop();
                            salario = salario + num;

                        }



                        //TODO SIGUIENDO EL EXCEL AHORA CALCULO LA FELICIDAD....
                        /*felicidadInicial = (1.0/b2*salario*200*b8)+(1.0/b3*b9)+(1.0/b4*b10)+(1.0/b5*b11);
                        System.out.println(felicidadInicial);
                        felicidadFinal = (1.0/b2*salario*200*c2)+(1.0/b3*c3)+(1.0/b4*c4)+(1.0/b5*c5);
                        System.out.println(felicidadFinal);*/

                        t = new Test(idUser, b2, b3, b4, b5, c2, c4, c3, c5, b8, b9, b10, b11, situacionLaboral, estadoCivil, numHijos);

                        System.out.println("PUTAAAAAAA");
                        System.out.println(t.getNHijos());
                        if(idUser>=0) {
                            Intent i = new Intent(TestActivity.this, ConfirmacionTestActivity.class);
                            i.putExtra("test", t);
                            i.putExtra("salario", salario);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(TestActivity.this, "Existen Campos sin contestar", Toast.LENGTH_SHORT).show();
                        }






                        Toast.makeText(TestActivity.this, "Todo Correcto", Toast.LENGTH_SHORT).show();
                    }
                    System.out.println("CCCCCCCCCCCCCCCCCCCC");
                    System.out.println(expediente);
                }

            }



        });







    }

    public void obtenerUser(String token){

       // String token = cargarPreferencias();
        System.out.println(token);


        final APIService apiService = RetroClass.getAPIService();

        user = new User();

        Call<User> u = apiService.userProfileTest(token);

        u.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                user = response.body();

                expediente = user.getExpediente();
                idUser = user.getId_user();
                System.out.println("XXXXXXXXXXXXXXXXXX");
                System.out.println("XXXXXXXXXXXXXXXXXX");
                System.out.println(idUser);
                System.out.println(expediente);
                btnEnviar.setEnabled(true);


                //TODO AÑADIR LA LÓGICA

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //expediente = 0;



            }


        });

//return expediente;

    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}