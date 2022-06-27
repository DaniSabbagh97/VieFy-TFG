package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.biometricthings.R;
import com.example.biometricthings.model.PasarBytes;
import com.github.barteksc.pdfviewer.PDFView;

public class PrevisualizarPracticaActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previsualizar_practica);

        PDFView pdfView = findViewById(R.id.pdfView);

        PasarBytes pb = getIntent().getParcelableExtra("pb");

        byte[] pdf = pb.getPdf();

        pdfView.fromBytes(pdf).load();

    }
}