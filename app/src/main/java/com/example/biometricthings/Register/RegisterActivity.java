package com.example.biometricthings.Register;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biometricthings.BiometricsActivity;
import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.Test.TestActivity;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etNumExpediente;
    private EditText etContrasenia;
    private EditText etConfirmaContrasenia;

    private String nombre, mail, psw, apellido, telf, psw2;
    private boolean finished;
    private boolean isProfe = false;
    private int isProfeInt;
    private boolean acceptPrivacity = false;
    private int idUserObtained;
    private String rol;


    private int numExp;


    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etEmail = (EditText) findViewById(R.id.etMail);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etContrasenia = (EditText) findViewById(R.id.etContrasenia1);
        etConfirmaContrasenia = (EditText) findViewById(R.id.etContrasenia2);
        etNumExpediente = (EditText) findViewById(R.id.etNumExpediente);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = etNombre.getText().toString().trim();
                mail = etEmail.getText().toString().trim();
                apellido = etApellido.getText().toString().trim();
                telf = etTelefono.getText().toString().trim();
                psw = etContrasenia.getText().toString().trim();
                psw2 = etConfirmaContrasenia.getText().toString().trim();
                numExp = Integer.parseInt(etNumExpediente.getText().toString().trim());



                final APIService apiService = RetroClass.getAPIService();
                if(isProfe){
                    isProfeInt=1;
                    rol = "Profesor";
                }else{
                    isProfeInt=0;
                    rol = "Alumno";
                }

                //TODO FIXME isProfeInt va a 0 idk why
                User u = new User(numExp, nombre, apellido, mail, telf, psw, isProfeInt, rol);

                Call<Integer> registro = apiService.registerUser(u);
                registro.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response!=null){
                            //finished = response.body();
                            //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            idUserObtained = response.body();
                            System.out.println("PPPPPPPPPPPPPPPPPP");
                            System.out.println(idUserObtained);
                            if(idUserObtained>0){
                                if(acceptPrivacity) {
                                    if(isProfeInt==1){
                                        Intent i = new Intent(RegisterActivity.this, ConfirmacionProfesorActivity.class);
                                        i.putExtra("id", idUserObtained);
                                        i.putExtra("mail", u.getEmail());
                                        startActivity(i);
                                        finish();
                                    }else {
                                        Intent i = new Intent(RegisterActivity.this, LogInActivity.class);//TODO CAMBIAR AL LOGIN PARA QUE INICIE SESION
                                        // i.putExtra("idUserObtained",idUserObtained);
                                        i.putExtra("mail", u.getEmail());
                                        startActivity(i);
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Debes aceptar la política de privacidad", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this, "No se pudo realizar el registro.", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            finished = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        finished = false;
                        Toast.makeText(RegisterActivity.this, "La base de datos no respondió.", Toast.LENGTH_SHORT).show();

                    }
                });

                if(finished){
                    Intent i = new Intent(RegisterActivity.this, BiometricsActivity.class);
                    startActivity(i);
                }



            }
        });


    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.cbProfe:
                if (checked){
                    isProfe = true;
                }else {

                    break;
                }
            case R.id.cbPrivacidad:
                if (checked) {
                    acceptPrivacity = true;
                }else {

                    break;
                }

        }
    }
}