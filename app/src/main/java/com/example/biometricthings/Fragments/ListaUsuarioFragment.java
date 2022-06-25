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

import com.example.biometricthings.Profesor.ListAdapterClases;
import com.example.biometricthings.Profesor.ListAdapterListaAlumnos;
import com.example.biometricthings.Profesor.SeleccionaClaseActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaUsuarioFragment extends Fragment {

    View view;
    private String token;
    private ArrayList<User> uu;
    private int idClase;
    private Clase c;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaUsuarioFragment newInstance(String param1, String param2) {
        ListaUsuarioFragment fragment = new ListaUsuarioFragment();
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
        view = inflater.inflate(R.layout.fragment_lista_usuario, container, false);
        uu = new ArrayList<User>();
        token = cargarPreferencias();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idClase = bundle.getInt("idClase", 0);
        }



        System.out.println("CLASEEEEEEEE");
        System.out.println(idClase);
        c = new Clase(idClase);

        final APIService apiService = RetroClass.getAPIService();

        Call<ArrayList<User>> u = apiService.getListaUsers(c,token);

        u.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                uu = response.body();

                ListAdapterListaAlumnos listAdapterListaAlumnos = new ListAdapterListaAlumnos(uu, getActivity());
                RecyclerView recyclerView = view.findViewById(R.id.rvListaAlumnos);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(listAdapterListaAlumnos);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

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