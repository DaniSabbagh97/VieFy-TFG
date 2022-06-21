package com.example.biometricthings.Propiedades;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.TextView;

/*import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;*/
import com.example.biometricthings.BiometricsActivity;
import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.Roles.CreaEmpresaActivity;
import com.example.biometricthings.Roles.EspecificacionesEmpresa;
import com.example.biometricthings.Roles.UneteEmpresaActivity;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.SliderData;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MostrarPropiedadActivity extends AppCompatActivity {

    private Propiedades propiedades;
    private int id;
    private String nombre;
    private double precio;
    private String habitaciones;
    private String img1;
    private String municipio;
    private String descripcion;
    private String Baños;
    private String metros_2;
    private String zona;
    private String img2;
    private String img3;
    private String tipo;

    private String rolUsuario;

    private String tokenDevuelto;
    private boolean terraza;
    private boolean pisicina;
    private boolean aireAcondicionado;
    private boolean parking;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tvInfo;
    private Button btnSi;
    private Button btnNo;

    private User u;



    private TextView tvNombre, tvDescripcion, tvPrecio;
    private Button btnAtras, btnSeleccionar;

  //  private ImageSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_propiedad);

        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        tvPrecio = (TextView) findViewById(R.id.tvPrecio);
        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnSeleccionar = (Button) findViewById(R.id.btnSeleccionar);
        //slider = findViewById(R.id.slider);
        SliderView sliderView = findViewById(R.id.slider);



        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt("id");
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIII");
            System.out.println(id);
            nombre = extras.getString("nombre");
            descripcion = extras.getString("descripcion");
            precio = extras.getDouble("precio");
            img1 = extras.getString("img1");
            img2 = extras.getString("img2");
            img3 = extras.getString("img3");
            tipo = extras.getString("tipo");
            String precioString = String.valueOf(precio);
            tvNombre.setText(nombre);
            tvDescripcion.setText(descripcion);
            tvPrecio.setText(precioString);



        }
        tokenDevuelto=  cargarPreferencias();

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        sliderDataArrayList.add(new SliderData(img1));
        sliderDataArrayList.add(new SliderData(img2));
        sliderDataArrayList.add(new SliderData(img3));
        SliderAdapter adapter = new SliderAdapter(MostrarPropiedadActivity.this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(5);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                propiedades = new Propiedades(id);
                createNewDialog(propiedades, tipo, precio);


            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MostrarPropiedadActivity.this, PropiedadesActivity.class);
                startActivity(i);
                finish();
            }
        });




       /* List<SlideModel> slideModels = new ArrayList<>();
        //slideModels.add(new SlideModel())*/
    }

    public void createNewDialog(Propiedades prop, String tipoPropiedad, double precio){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpView = getLayoutInflater().inflate(R.layout.popup_confirmacion_propiedad, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);
        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("¿Está seguro de haber elegido correctamente la propiedad?\nNo podrá cambiar su elección");

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipoPropiedad.equals("vivienda")){
                final APIService apiService = RetroClass.getAPIService();
                Call<String> registroProp = apiService.registroPropiedad(prop, tokenDevuelto);
                registroProp.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null) {
                            rolUsuario = response.body();

                            if (rolUsuario.equals("Empresario")) {//TODO HACER LA PARTE DE CREAR EMPRESA Y SELECCIONAR LOCAL PARA LA EMPRESA
                                Intent i = new Intent(MostrarPropiedadActivity.this, CreaEmpresaActivity.class);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            } else if (rolUsuario.equals("Asalariado")) {
                                Intent i = new Intent(MostrarPropiedadActivity.this, UneteEmpresaActivity.class);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            } else if (rolUsuario.equals("Autonomo")) {
                                Intent i = new Intent(MostrarPropiedadActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                                dialog.dismiss();

                            }
                        } else {
                            System.out.println("MAAAAAAAAAAAAAAAL2");
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }else if(tipoPropiedad.equals("local")){
                    Intent i = new Intent(MostrarPropiedadActivity.this, EspecificacionesEmpresa.class);
                    i.putExtra("idProp", prop.getId_propiedades());//TODO ENVIAR MÁS DATOS
                    i.putExtra("precio", precio);//TODO ENVIAR MÁS DATOS
                    startActivity(i);
                }






            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

}