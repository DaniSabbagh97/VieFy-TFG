package com.example.biometricthings.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

/*

FIXME AAAAAAAAAAAAAAAAAAAAAAAAAAA el tokeeeeeeeeeeeeen

 */
public class SalarioEmpresarioFragment extends Fragment {

    private EditText etEstablecerSueldo;

    private Button btnModificar;
    private Button btnGuardar;
    private String token, token2;

    private int salario;

    private View view;

    private User u;
    private User u2;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SalarioEmpresarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalarioEmpresarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalarioEmpresarioFragment newInstance(String param1, String param2) {
        SalarioEmpresarioFragment fragment = new SalarioEmpresarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_salario_empresario, container, false);
        token = cargarPreferencias();



        final APIService apiService = RetroClass.getAPIService();
        Call<User> getSalario = apiService.getSalario(token);

        getSalario.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body()!=null){
                    u = response.body();

                    etEstablecerSueldo.setText(u.getSalario()+"");
                }else{
                    Toast.makeText(view.getContext(), "No existe ningún sueldo establecido", Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        etEstablecerSueldo = view.findViewById(R.id.etEstablecerSueldo);
        etEstablecerSueldo.setEnabled(false);

        btnModificar = view.findViewById(R.id.btnModificar);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGuardar.setEnabled(true);
                etEstablecerSueldo.setEnabled(true);






            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        btnModificar.setEnabled(false);
                        if(!etEstablecerSueldo.getText().equals("")) {
                            salario= Integer.parseInt(etEstablecerSueldo.getText().toString().trim());
                        }
                        System.out.println("SALARIOOO");
                        System.out.println(salario);

                        u2 = new User(0,salario);

                        token2 = cargarPreferencias();

                        final APIService apiService = RetroClass.getAPIService();
                        Call<Boolean> getSalario = apiService.setSalario(u2, token2);

                        getSalario.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body()!=null){
                                    if(response.body()){

                                        etEstablecerSueldo.setText("");
                                        etEstablecerSueldo.setEnabled(false);
                                        Intent i = new Intent(getActivity().getBaseContext(), HomeActivity.class);
                                        startActivity(i);


                                    }else{
                                        Toast.makeText(view.getContext(), "Insertó algo incorrecto, vuelva a intentarlo...", Toast.LENGTH_SHORT).show();
                                    }



                                }else{
                                    Toast.makeText(view.getContext(), "No se pudo conectar con la base de datos", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                                System.out.println(t.getMessage());

                            }
                        });

                    }
                });



        return view;
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getActivity().getBaseContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }
}