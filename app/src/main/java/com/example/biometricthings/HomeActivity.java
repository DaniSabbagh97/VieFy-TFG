package com.example.biometricthings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.biometricthings.Fragments.HomeFragment;
import com.example.biometricthings.Fragments.ListaUsuarioFragment;
import com.example.biometricthings.Fragments.ProfileFragment;
import com.example.biometricthings.Fragments.WorkFragment;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.PDF.ReadPDFActivity;
import com.example.biometricthings.Profesor.SeleccionaClaseActivity;
import com.example.biometricthings.SplashArt.SplashArtActivity;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    WorkFragment workFragment = new WorkFragment();
    ListaUsuarioFragment listaUsuarioFragment = new ListaUsuarioFragment();
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private CircleImageView ivUserPerfil;
    private String imagenRecibida;
    private TextView tvPrueba;
    private int isProfesor;
    private String rol;
    Toolbar toolbar;
    private int idClase;
    ChipNavigationBar chipNavigationBar;

    User user;
//TODO https://www.youtube.com/watch?v=m1RV0HPuBWo&list=PL5jb9EteFAOD8dlG1Il3fCiaVNPD_P7gh&index=2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);
        ivUserPerfil = (CircleImageView) headerView.findViewById(R.id.ivUserPerfil);


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            idClase = extras.getInt("idClase");
        }
        tvPrueba = headerView.findViewById(R.id.tvPrueba);



        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        chipNavigationBar = findViewById(R.id.chipBottomBar);
        bottomMenu();
        loadFragment(profileFragment);

        String token2 = cargarPreferencias();


        final APIService apiService = RetroClass.getAPIService();

        user = new User();

        Call<User> u = apiService.userProfile(token2);//TODO LLAMAR A LA RUTA USERPROFILE DSDE LA API, DESDE USERS

        u.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                System.out.println(response.body());
                System.out.println("bbbbbbbbbbbbbbbbb");
                System.out.println(user.getNombre());
                imagenRecibida = user.getImagen();

                byte[] bytes= Base64.decode(imagenRecibida,Base64.DEFAULT);

                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                //String prueba = Base64.encodeToString(bytes, Base64.DEFAULT);
                ivUserPerfil.setImageBitmap(bitmap);

                tvPrueba.setText(user.getNombre() + " "+user.getapellidos());
                isProfesor = user.getIsProfe();
                rol = user.getRol_juego();
                Menu menu = navigationView.getMenu();
                if(isProfesor==1){
                    //TODO ES PROFESOR

                    menu.findItem(R.id.nav_banco).setVisible(false);
                    menu.findItem(R.id.nav_trabajo).setVisible(false);
                    menu.findItem(R.id.nav_principal).setVisible(false);
                    menu.findItem(R.id.nav_empresa).setVisible(false);
                    menu.findItem(R.id.nav_personal).setVisible(false);

                }else if(isProfesor==0){

                    //TODO ES ALUMNO
                    menu.findItem(R.id.nav_listaAlumnos).setVisible(false);
                    menu.findItem(R.id.nav_practicas).setVisible(false);
                    menu.findItem(R.id.nav_cambiarClase).setVisible(false);
                    menu.findItem(R.id.nav_mensajes).setVisible(false);
                    if(rol.equals("Empresario")){
                        //  TODO MENÚ DE EMPRESARIO
                        menu.findItem(R.id.nav_principal).setVisible(false);

                    }else if(rol.equals("Asalariado")){
                        //TODO MENÚ DE ASALARAIDO
                        menu.findItem(R.id.nav_empresa).setVisible(false);
                        menu.findItem(R.id.nav_personal).setVisible(false);

                    }else if(rol.equals("Autonomo")){
                        //TODO MENÚ DE AUTONOMO
                        menu.findItem(R.id.nav_empresa).setVisible(false);
                        menu.findItem(R.id.nav_personal).setVisible(false);

                    }

                }
                /*tvNombre.setText(user.getNombre());
                tvEmail.setText(user.getEmail());
                tvApellido.setText(user.getapellidos());*/

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("bbbbbbbbbbbbbbbbb");
                System.out.println("bbbbbbbbbbbbbbbbb");
            }
        });








    }

    private void bottomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.profileFragment:
                        loadFragment(profileFragment);
                        break;
                    case R.id.homeFragment:
                        loadFragment(listaUsuarioFragment);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idClase", idClase);
                        listaUsuarioFragment.setArguments(bundle);
                        break;

                    case R.id.workFragment:
                        loadFragment(workFragment);
                        break;



                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }


   /* private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {//Gestión del menú del usuario normal
            switch (item.getItemId()){
                case R.id.profileFragment:
                    loadFragment(profileFragment);
                    return true;
                case R.id.homeFragment:
                    loadFragment(homeFragment);
                    return true;

                case R.id.workFragment:
                    loadFragment(workFragment);
                    return true;



            }


            return false;
        }
    };*/

    public void loadFragment(Fragment fragment){//Metodo para cargar los fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();

    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_principal:
                loadFragment(profileFragment);
                /*Intent i = new Intent(HomeActivity.this, ReadPDFActivity.class);
                startActivity(i);*/
                break;
            case R.id.nav_banco:
                loadFragment(homeFragment);
                /*Intent i2 = new Intent(HomeActivity.this, LoadPDFActivity.class);
                startActivity(i2);*/
                break;
            case R.id.nav_logout:
                limpiarPreferencias();
                Intent i = new Intent(HomeActivity.this, SplashArtActivity.class);
                startActivity(i);
                finish();
                /*Intent i2 = new Intent(HomeActivity.this, LoadPDFActivity.class);
                startActivity(i2);*/
                break;
            case R.id.nav_cambiarClase:
                Intent i2 = new Intent(HomeActivity.this, SeleccionaClaseActivity.class);
                startActivity(i2);
                finish();
                /*Intent i2 = new Intent(HomeActivity.this, LoadPDFActivity.class);
                startActivity(i2);*/
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void limpiarPreferencias(){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token","NO");
        editor.putInt("idUser",0);
        editor.commit();

    }
}