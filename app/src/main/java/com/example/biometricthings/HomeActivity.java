package com.example.biometricthings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.biometricthings.Fragments.HomeFragment;
import com.example.biometricthings.Fragments.ProfileFragment;
import com.example.biometricthings.Fragments.WorkFragment;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.PDF.ReadPDFActivity;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    WorkFragment workFragment = new WorkFragment();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    User user;
//TODO https://www.youtube.com/watch?v=m1RV0HPuBWo&list=PL5jb9EteFAOD8dlG1Il3fCiaVNPD_P7gh&index=2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



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

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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
    };

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
            case R.id.nav_2:
                loadFragment(profileFragment);
                /*Intent i = new Intent(HomeActivity.this, ReadPDFActivity.class);
                startActivity(i);*/
                break;
            case R.id.nav_3:
                loadFragment(homeFragment);
                /*Intent i2 = new Intent(HomeActivity.this, LoadPDFActivity.class);
                startActivity(i2);*/
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}