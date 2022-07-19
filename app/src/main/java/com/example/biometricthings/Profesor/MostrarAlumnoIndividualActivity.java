package com.example.biometricthings.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biometricthings.Fragments.ListaUsuarioFragment;
import com.example.biometricthings.HomeActivity;
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
    private ListaUsuarioFragment listaUsuarioFragment = new ListaUsuarioFragment();

    private EditText etCorreo;
    private EditText etExpediente;
    private EditText etFelicidad;


    private Button btnAtrass;
    private Button btnGuardar;

    private String nombre, apellido, rol, correo, imagen, expedienteString, token;
    private int expediente, id, saldo, idEmpresa;
    private float saldo2;
    private User user;
    private User uEnviar;


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
        etFelicidad = (EditText) findViewById(R.id.etFelicidad);

        //btnModificar = (Button) findViewById(R.id.btnModificar);
        btnAtrass = (Button) findViewById(R.id.btnAtrass);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt("id");
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIII");
            System.out.println(id);
            nombre = extras.getString("nombre");
            apellido = extras.getString("apellido");
            rol = extras.getString("rol");
            expediente = extras.getInt("expediente");
           // imagen = extras.getString("imagen");
            saldo2 = extras.getFloat("saldo");
            idEmpresa = extras.getInt("idEmpresa");

            expedienteString = String.valueOf(expediente);

            correo = extras.getString("correo");
          //  imagen = extras.getString("img");
           user = new User();

           token = cargarPreferencias();
            uEnviar = new User(id);
            final APIService apiService = RetroClass.getAPIService();

            Call<User> u = apiService.getImagenPerfil(uEnviar);

            u.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body()!=null){
                        user = response.body();
                        imagen = user.getImagen();

                        byte[] bytes= Base64.decode(imagen,Base64.DEFAULT);

                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                        imagenPerfil.setImageBitmap(bitmap);
                        tvDineroPersonal.setText(saldo2+"");
                        tvNombreCompleto.setText(nombre+" "+apellido);
                        tvRolJuego.setText(rol);
                        etCorreo.setText(correo);
                        etExpediente.setText(expedienteString);


                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });

           // u.enqueue(new Callback<User>() {






        }

        btnAtrass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MostrarAlumnoIndividualActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

//TODO LLamar a la API recoger el saldo actual en cuentaParticular y obtener el saldo de empresa que está dentro de la emrpesa
        //TODO A través del id_user
       /* final APIService apiService = RetroClass.getAPIService();

        user = new User();

        Call<User> u = apiService.userProfile(token2);//TODO LLAMAR A LA RUTA USERPROFILE DSDE LA API, DESDE USERS

        u.enqueue(*/



    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);//FIXME O SIN getBaseContext().

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

    public void loadFragment(Fragment fragment){//Metodo para cargar los fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();

    }
}