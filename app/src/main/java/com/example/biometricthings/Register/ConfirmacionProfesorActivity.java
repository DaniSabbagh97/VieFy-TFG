package com.example.biometricthings.Register;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.Roles.MostrarEmpresaIndividualActivity;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

public class ConfirmacionProfesorActivity extends AppCompatActivity {

    private EditText etCodProfesor;
    private Button btnComprobar;
    private String codigo;
    private int id;
    private String mail;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_profesor);

        etCodProfesor = (EditText) findViewById(R.id.etCodProfesor);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
          id = extras.getInt("id");
          mail = extras.getString("mail");
        }

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                codigo = etCodProfesor.getText().toString().trim();

                if(codigo.equals("maesebit2022")){
                    Intent i = new Intent(ConfirmacionProfesorActivity.this, LogInActivity.class);
                    // i.putExtra("idUserObtained",idUserObtained);
                    i.putExtra("mail", mail);
                    startActivity(i);
                    finish();
                }else{
                    u = new User(id);

                    final APIService apiService = RetroClass.getAPIService();

                    Call<Boolean> delete = apiService.borrar(u);

                    delete.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            if(response.body()){
                                Intent i = new Intent(ConfirmacionProfesorActivity.this, RegisterActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                            System.out.println(t.getMessage());

                        }
                    });


                }

            }
        });
    }
}