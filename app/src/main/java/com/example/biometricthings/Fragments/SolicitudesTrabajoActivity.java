package com.example.biometricthings.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.biometricthings.Profesor.ListAdapterListaAlumnos;
import com.example.biometricthings.R;
import com.example.biometricthings.Roles.ListAdapterSolicitudes;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Solicitud;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolicitudesTrabajoActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicitudesTrabajoActivity extends Fragment {

    View view;
    private String token;
    private Empresa ee;
    private ArrayList<Solicitud> arr;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicitudesTrabajoActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolicitudesTrabajoActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static SolicitudesTrabajoActivity newInstance(String param1, String param2) {
        SolicitudesTrabajoActivity fragment = new SolicitudesTrabajoActivity();
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
        view = inflater.inflate(R.layout.fragment_solicitudes_trabajo_activity, container, false);

        ee = new Empresa();
        arr = new ArrayList<Solicitud>();
        token = cargarPreferencias();

        final APIService apiService = RetroClass.getAPIService();

        Call<Empresa> l = apiService.getListaSolicitudes(token);

        l.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {

                if(response.body()!=null) {
                    ee = response.body();
                    System.out.println(ee);
                    arr = ee.getSolicitudes();

                /*for (Solicitud s : arr){

                }*/

                    ListAdapterSolicitudes listAdapterSolicitudes = new ListAdapterSolicitudes(arr, getActivity());
                    RecyclerView recyclerView = view.findViewById(R.id.rvListaSolicitudes);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(listAdapterSolicitudes);
                }else{
                    Toast.makeText(view.getContext(), "No hay solicitudes Aún", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {

                System.out.println(t.getMessage());

            }
        });


        return view;
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");



        return tokenFinal;


    }
}