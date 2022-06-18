package com.example.biometricthings.Roles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.biometricthings.Propiedades.PropiedadesActivity;
import com.example.biometricthings.R;

public class CreaEmpresaActivity extends AppCompatActivity {
    //private EditText etNombreEmpresa;
    private Button btnSiguiente;
    private String nombreEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_empresa);

        //etNombreEmpresa = (EditText) findViewById(R.id.etNombreEmpresa);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nombreEmpresa = etNombreEmpresa.getText().toString().trim();
                Intent i = new Intent(CreaEmpresaActivity.this, PropiedadesActivity.class);
              //  i.putExtra("empresa", nombreEmpresa);
                i.putExtra("key", 2);
                startActivity(i);
            }
        });
    }
}