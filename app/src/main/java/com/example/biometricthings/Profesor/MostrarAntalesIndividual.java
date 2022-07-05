package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;


import com.example.biometricthings.R;

public class MostrarAntalesIndividual extends AppCompatActivity {

    private TextView tvSaldoPorAlumno;
    private TextView tvSaldoReal;
    private TextView tvBeneficioPorAlumno;
    private TextView tvBeneficioReal;
    private TextView tvRolJuegoAlumno;
    private TextView tvTipoAntal;
    private TextView tvMensajeAlumno;
    private TextView tvNombreApellidos;
    private CircleImageView ivAlumno;
    private Button btnCorrecto;
    private Button btnMultar;
    private String nombre, apellido, imagen, rol, tipoAntal, mensaje;
    private int id, saldoReal, saldoAlumno, beneficioAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_antales_individual);

        tvSaldoPorAlumno = (TextView) findViewById(R.id.tvSaldoPorAlumno);
        tvSaldoReal = (TextView) findViewById(R.id.tvSaldoReal);
        tvBeneficioPorAlumno = (TextView) findViewById(R.id.tvBeneficioPorAlumno);
        tvBeneficioReal = (TextView) findViewById(R.id.tvBeneficioReal);
        tvRolJuegoAlumno = (TextView) findViewById(R.id.tvRolJuegoAlumno);
        tvTipoAntal = (TextView) findViewById(R.id.tvTipoAntal);
        tvMensajeAlumno = (TextView) findViewById(R.id.tvMensajeAlumno);
        tvNombreApellidos = (TextView) findViewById(R.id.tvNombreApellidos);

        ivAlumno = (CircleImageView) findViewById(R.id.ivAlumno);
        btnCorrecto = (Button) findViewById(R.id.btnCorrecto);
        btnMultar = (Button) findViewById(R.id.btnMultar);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){


            id = extras.getInt("id");
            nombre = extras.getString("nombre");
            apellido = extras.getString("apellido");
            rol = extras.getString("rol");
            saldoReal = extras.getInt("saldoReal");
            saldoAlumno = extras.getInt("saldoPorAlumno");
            tipoAntal = extras.getString("tipoAntal");
            imagen = extras.getString("imagen");
            beneficioAlumno = extras.getInt("BeneficioPorAlumno");
            mensaje = extras.getString("Mensaje");

            byte[] bytes= Base64.decode(imagen,Base64.DEFAULT);

            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            ivAlumno.setImageBitmap(bitmap);
            System.out.println("OOOOOOOOO");
            System.out.println(saldoReal+"  "+saldoAlumno);
            tvNombreApellidos.setText(nombre+" "+apellido);
            tvSaldoPorAlumno.setText(saldoAlumno+"");
            tvSaldoReal.setText(saldoReal+"");
            tvBeneficioPorAlumno.setText(beneficioAlumno+"");
            tvBeneficioReal.setText("");
            tvRolJuegoAlumno.setText(rol+"");
            tvTipoAntal.setText(tipoAntal+"");
            tvMensajeAlumno.setText(mensaje+"");



            //TODO DISEÑOOOOOOOOOOOOOOO


        }

    }
}