package com.example.biometricthings.Roles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Profesor.PrevisualizarPracticaActivity;
import com.example.biometricthings.Profesor.SubidaPracticas;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Solicitud;
import com.example.biometricthings.model.SolicitudAceptada;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterSolicitudes extends RecyclerView.Adapter<ListAdapterSolicitudes.ViewHolder>{

    private List<Solicitud> mData;
    private Context context;
    private String mensaje;
    byte[] pdf;
    private PasarBytes pb;
    private byte[] inputData;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private int idEmpresa;
    private String salarioString;
    private int salarioInt;
    private String token;
    private SolicitudAceptada sa;
    private int idSolicitud;

    private TextView tvInfo;
    private EditText etSalario;
    private Button btnSi, btnNo;
    private User u;

    public ListAdapterSolicitudes(List<Solicitud> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterSolicitudes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_solicitud, parent, false);

        return new ListAdapterSolicitudes.ViewHolder(view);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMensaje;
        Button btnVerCV;
        Button btnAcpetarAlumno;
        Button btnRechazarAlumno;
        private AlertDialog.Builder dialogBuilder;
        private AlertDialog dialog;

        ViewHolder(View itemView){
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            btnVerCV = itemView.findViewById(R.id.btnVerCV);
            btnAcpetarAlumno = itemView.findViewById(R.id.btnAcpetarAlumno);
            btnRechazarAlumno = itemView.findViewById(R.id.btnRechazarAlumno);
            u = new User();


            SharedPreferences preferences = itemView.getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            token = preferences.getString("token","No existe el token");

            btnVerCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdf = mData.get(getAdapterPosition()).getPdf();
                    pb = new PasarBytes(pdf);

                    Intent i = new Intent(view.getContext(), PrevisualizarPracticaActivity.class);
                    i.putExtra("pb",pb);
                    view.getContext().startActivity(i);

                }
            });

            btnAcpetarAlumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO DIALOG QUE PREGUNTE SI ESTÁ SEGURO EN ACEPTARLE
                    idSolicitud = mData.get(getAbsoluteAdapterPosition()).getId_solicitud();
                    u = mData.get(getAdapterPosition()).getUser();
                    createNewDialogAceptar(itemView, idSolicitud);
                }
            });

            btnRechazarAlumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO DIALOG QUE PREGUNTE SI ESTÁ SEGURO EN RECHAZARLE
                    idSolicitud = mData.get(getAbsoluteAdapterPosition()).getId_solicitud();
                    u = mData.get(getAdapterPosition()).getUser();
                    createNewDialogRechazar(itemView, idSolicitud);
                }
            });


        }

        void bindData(final Solicitud item){

        }

        }


    @Override
    public void onBindViewHolder(final ListAdapterSolicitudes.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));


        mensaje = mData.get(position).getMensaje();


        holder.tvMensaje.setText(mensaje);

    }

    public void createNewDialogAceptar(View v, int id){
        dialogBuilder = new AlertDialog.Builder(v.getContext());
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popUpView = li.inflate(R.layout.popup_contrato_salario, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);
        etSalario = (EditText) popUpView.findViewById(R.id.etSalario);
        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("¿Estás seguro de la acción que estás realizando?");

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                salarioInt = Integer.parseInt((etSalario.getText().toString().trim()));

                sa = new SolicitudAceptada(id,salarioInt);

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> si = apiService.contratarAsalariado(sa,token);
                si.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                        if(response.body()){
                            Intent i = new Intent(v.getContext(), HomeActivity.class);
                            v.getContext().startActivity(i);
                            Toast.makeText(context, "Contratado!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });


            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void createNewDialogRechazar(View v, int id){
        dialogBuilder = new AlertDialog.Builder(v.getContext());
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popUpView = li.inflate(R.layout.popup_confirmacion_propiedad, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);
        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("¿Estás seguro de la acción que estás realizando?");

        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sa = new SolicitudAceptada(idSolicitud);

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> no = apiService.borrarSolicitud(sa,token);
                no.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                        if(response.body()){
                            Intent i = new Intent(v.getContext(), HomeActivity.class);
                            v.getContext().startActivity(i);
                            Toast.makeText(context, "Solicitud Eliminada", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });

                //TODO RECHAZA LA SOLICITUD, POR LO QUE LA BORRA DE LA BBDD

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



}

