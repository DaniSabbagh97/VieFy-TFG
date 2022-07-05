package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biometricthings.R;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

public class MostrarAlumnoIndividualActivity extends AppCompatActivity {

    private ImageView imagenPerfil;

    private TextView tvNombreCompleto;
    private TextView tvRolJuego;
    private TextView tvDineroEmpresa;
    private TextView tvDineroPersonal;

    private EditText etCorreo;
    private EditText etExpediente;


    private Button btnModificar;
    private Button btnGuardar;

    private String nombre, apellido, rol, correo, imagen, expedienteString;
    private int expediente, id, saldo, idEmpresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_alumno_individual);

        imagenPerfil = (ImageView) findViewById(R.id.imagenPerfil);

        tvNombreCompleto = (TextView) findViewById(R.id.tvNombreCompleto);
        tvRolJuego = (TextView) findViewById(R.id.tvRolJuego);
        tvDineroEmpresa = (TextView) findViewById(R.id.tvDineroEmpresa);
        tvDineroPersonal = (TextView) findViewById(R.id.tvDineroPersonal);

        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etExpediente = (EditText) findViewById(R.id.etExpediente);

        btnModificar = (Button) findViewById(R.id.btnModificar);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt("id");
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIII");
            System.out.println(id);
            nombre = extras.getString("nombre");
            apellido = extras.getString("apellido");
            rol = extras.getString("rol");
            expediente = extras.getInt("expediente");
            imagen = extras.getString("imagen");
            saldo = extras.getInt("saldo");
            idEmpresa = extras.getInt("idEmpresa");

            expedienteString = String.valueOf(expediente);

            correo = extras.getString("correo");
            imagen = extras.getString("img");

            byte[] bytes= Base64.decode(imagen,Base64.DEFAULT);

            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            imagenPerfil.setImageBitmap(bitmap);
            tvDineroPersonal.setText(idEmpresa+"");
            tvNombreCompleto.setText(nombre+" "+apellido);
            tvRolJuego.setText(rol);
            etCorreo.setText(correo);
            etExpediente.setText(expedienteString);




        }

//TODO LLamar a la API recoger el saldo actual en cuentaParticular y obtener el saldo de empresa que está dentro de la emrpesa
        //TODO A través del id_user
       /* final APIService apiService = RetroClass.getAPIService();

        user = new User();

        Call<User> u = apiService.userProfile(token2);//TODO LLAMAR A LA RUTA USERPROFILE DSDE LA API, DESDE USERS

        u.enqueue(*/



    }
}