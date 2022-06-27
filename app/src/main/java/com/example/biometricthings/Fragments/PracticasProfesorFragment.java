package com.example.biometricthings.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biometricthings.Image.Utils;
import com.example.biometricthings.PDF.LoadPDFActivity;
import com.example.biometricthings.Profesor.PrevisualizarPracticaActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticasProfesorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticasProfesorFragment extends Fragment {

    private TextView tvArchivo;
    View view;
    private EditText etNombrePractica;
    private EditText etValorPractica;
    private EditText etCantidadEjercicios;
    private EditText etFechaEntrega;

    private Button btnAdjuntarPDF;
    private Button btnPrevisualizacion;
    private Button btnSubirPractica;

    private byte[] inputData;
    private PasarBytes pb;

    private Context context;
    private int SELECT_FILE = 200;
    private String token;
    private String nombrePDF;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PracticasProfesorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticasProfesorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticasProfesorFragment newInstance(String param1, String param2) {
        PracticasProfesorFragment fragment = new PracticasProfesorFragment();
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
        view = inflater.inflate(R.layout.fragment_practicas_profesor, container, false);
        token = cargarPreferencias();
        tvArchivo = (TextView) view.findViewById(R.id.tvArchivo);
        etNombrePractica = (EditText) view.findViewById(R.id.etNombrePractica);
        etValorPractica = (EditText) view.findViewById(R.id.etValorPractica);
        etCantidadEjercicios = (EditText) view.findViewById(R.id.etCantidadEjercicios);
        etFechaEntrega = (EditText) view.findViewById(R.id.etFechaEntrega);

        btnAdjuntarPDF = (Button) view.findViewById(R.id.btnAdjuntarPDF);
        btnPrevisualizacion = (Button) view.findViewById(R.id.btnPrevisualizacion);
        btnSubirPractica = (Button) view.findViewById(R.id.btnSubirPractica);


        btnAdjuntarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasStoragePermission(getActivity())){
                    openImageChooser();
                }else{
                    ActivityCompat.requestPermissions(((AppCompatActivity) getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                }

            }
        });

        btnPrevisualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb = new PasarBytes(inputData);

                Intent i = new Intent(getActivity(), PrevisualizarPracticaActivity.class);
                i.putExtra("pb",pb);
                startActivity(i);

            }
        });

        btnSubirPractica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* final APIService apiService = RetroClass.getAPIService();
            Call<Boolean> upDocument = apiService.subirPDF(inputData, token);
            upDocument.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    System.out.println("OOOOOOOOOOOOOOOO");
                    System.out.println(response.body());

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });*/

            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escoge un archivo PDF"), SELECT_FILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    try{
                        InputStream iStream = getActivity().getApplicationContext().getContentResolver().openInputStream(selectedUri);
                        inputData = Utils.getBytes(iStream);
                        nombrePDF = getFileName(selectedUri);
                        tvArchivo.setText(nombrePDF);
                        btnPrevisualizacion.setEnabled(true);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

    }

    private boolean hasStoragePermission(Context context) {
        int read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return read == PackageManager.PERMISSION_GRANTED;
    }


    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    public String cargarPreferencias(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");



        return tokenFinal;


    }
}