package com.example.biometricthings.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biometricthings.BiometricsActivity;
import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.LogInActivity;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.Test.TestActivity;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etNumExpediente;
    private EditText etContrasenia;
    private EditText etConfirmaContrasenia;
    private ImageView ivPerfil;

    private int SELECT_IMAGE = 200;
    private Uri selectedImage;
    private byte[] inputData;
    private byte[] fotoSubir;
    private String encodedImage;
    private String imagenString;
    private String uriString;


    private String nombre, mail, psw, apellido, telf, psw2;

    private boolean isProfe = false;
    private int isProfeInt;
    private boolean acceptPrivacity = false;
    private int idUserObtained;
    private String rol;


    private int numExp;


    private Button btnRegistrarse;
    private Button btnSeleccionarFoto;

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
        ivPerfil = (ImageView) findViewById(R.id.ivPerfil);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnSeleccionarFoto = (Button) findViewById(R.id.btnSeleccionarFoto);

        btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(RegisterActivity.this)){
                    imageChooser();

                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) RegisterActivity.this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }

            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = etNombre.getText().toString().trim();
                mail = etEmail.getText().toString().trim();
                apellido = etApellido.getText().toString().trim();
                telf = etTelefono.getText().toString().trim();
                psw = etContrasenia.getText().toString().trim();
                psw2 = etConfirmaContrasenia.getText().toString().trim();
                if(!TextUtils.isEmpty(etNumExpediente.getText().toString())) {
                    numExp = Integer.parseInt(etNumExpediente.getText().toString().trim());
                }else{
                    Toast.makeText(RegisterActivity.this, "El número de expediente es imprescindible...", Toast.LENGTH_SHORT).show();
                }


                if(!nombre.isEmpty() && !mail.isEmpty() && !apellido.isEmpty() && !telf.isEmpty() && !psw.isEmpty() && !psw2.isEmpty() && !(TextUtils.isEmpty(etNumExpediente.getText().toString())) && selectedImage!=null){
                    if(psw.equals(psw2)){
                        if(psw.length()>=6){

                            final APIService apiService = RetroClass.getAPIService();
                            if(isProfe){
                                isProfeInt=1;
                                rol = "Profesor";
                            }else{
                                isProfeInt=0;
                                rol = "Alumno";
                            }

                            if(acceptPrivacity){

                                User u = new User(numExp, nombre, apellido, mail, telf, psw, isProfeInt, rol, encodedImage);
                                Call<Integer> registro = apiService.registerUser(u);
                                registro.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if(response!=null){
                                            //finished = response.body();
                                            //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                            idUserObtained = response.body();//FIXME PETA PORQUE HACE DEMASIADO
                                            System.out.println("PPPPPPPPPPPPPPPPPP");
                                            System.out.println(idUserObtained);
                                            if(idUserObtained>0){

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
                                                Toast.makeText(RegisterActivity.this, "No se pudo realizar el registro.", Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            Toast.makeText(RegisterActivity.this, "Error en la Base de Datos", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                        Toast.makeText(RegisterActivity.this, "La base de datos no respondió.", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }else{
                                Toast.makeText(RegisterActivity.this, "Debes Aceptar la Política de Privacidad o seleccionar una imagen", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(RegisterActivity.this, "La contraseña debe ser superior a 6 caracteres o números", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Las contraseñas deben coincidir...", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Debe rellenar todos los campos...", Toast.LENGTH_SHORT).show();
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
                    isProfe = false;
                    break;
                }
            case R.id.cbPrivacidad:
                if (checked) {
                    acceptPrivacity = true;
                }else {
                    acceptPrivacity = false;
                    break;
                }

        }
    }
    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }


    public void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Elige una imagen"), SELECT_IMAGE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_IMAGE){
                selectedImage = data.getData();
                Uri selectedUri = data.getData();
                if(selectedImage != null){
                    ivPerfil.setImageURI(selectedImage);
                    uriString = selectedUri.toString();
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        encodeImage(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }
    private void encodeImage(Bitmap bm)
    {




        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

    }

    private byte[] decodificador(Uri selectedUri){
        try {
            InputStream iStream = getContentResolver().openInputStream(selectedUri);
            inputData = Utils.getBytes(iStream);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return inputData;
    }
}