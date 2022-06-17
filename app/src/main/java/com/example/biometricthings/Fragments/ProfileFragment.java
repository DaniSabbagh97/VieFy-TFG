package com.example.biometricthings.Fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.example.biometricthings.R;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private TextView tvNombre;
    private TextView tvEmail;
    private TextView tvApellido;

    private Button btnBtn;
    private View view;



    User user;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvApellido = (TextView) view.findViewById(R.id.tvApellido);
        btnBtn = (Button) view.findViewById(R.id.btnBtn);

        String token2 = cargarPreferencias();


        final APIService apiService = RetroClass.getAPIService();

        user = new User();

        Call<User> u = apiService.userProfile(token2);

        u.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                System.out.println(response.body());
                System.out.println(user.getNombre());
                tvNombre.setText(user.getNombre());
                tvEmail.setText(user.getEmail());
                tvApellido.setText(user.getapellidos());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("NO FUNCIONÓ");
            }
        });



        btnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public String cargarPreferencias(){
        SharedPreferences preferences = getActivity().getBaseContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);//FIXME O SIN getBaseContext().

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }

}
