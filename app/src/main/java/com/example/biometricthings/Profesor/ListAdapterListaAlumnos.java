package com.example.biometricthings.Profesor;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Propiedades.ListAdapter;
import com.example.biometricthings.Propiedades.MostrarPropiedadActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Multa;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.SolicitudAceptada;
import com.example.biometricthings.model.User;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterListaAlumnos extends RecyclerView.Adapter<ListAdapterListaAlumnos.ViewHolder> {

    private List<User> mData;
    private Context context;
    private String nombre;
    private String imagen;
    private String apellido;
    private String rol;
    private int expediente;
    private String expedienteString;
    private String motivo;
    private int multa;
    private Multa m;
    private String token;

    //private int alumnos;


    private TextView tvInfo;
    private EditText etMulta;
    private EditText etMotivoMulta;
    private Button btnSi, btnNo;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    public ListAdapterListaAlumnos(List<User> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdapterListaAlumnos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_alumnos, parent, false);

        return new ListAdapterListaAlumnos.ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreApellidoAlumno,tvTipoRol, tvExpedienteAlumno;
        ImageView ivImagenAlumno;
        Button btnVerAlumno;
        Button btnMultarAlumno;

        ViewHolder(View itemView){
            super(itemView);
            tvNombreApellidoAlumno = itemView.findViewById(R.id.tvNombreApellidoAlumno);
            tvTipoRol = itemView.findViewById(R.id.tvTipoRol);
            tvExpedienteAlumno = itemView.findViewById(R.id.tvExpedienteAlumno);
            ivImagenAlumno = itemView.findViewById(R.id.ivImagenAlumno);

            btnVerAlumno = itemView.findViewById(R.id.btnVerAlumno);
            btnMultarAlumno = itemView.findViewById(R.id.btnMultarAlumno);

            btnVerAlumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), MostrarAlumnoIndividualActivity.class);//TODO VER ALUMNO

                    i.putExtra("id", mData.get(getAdapterPosition()).getId_user());//FIXME
                    i.putExtra("nombre", mData.get(getAdapterPosition()).getNombre());
                    i.putExtra("apellido", mData.get(getAdapterPosition()).getApellidos());
                    i.putExtra("rol", mData.get(getAdapterPosition()).getRol_juego());
                    i.putExtra("expediente", mData.get(getAdapterPosition()).getExpediente());
                    i.putExtra("correo", mData.get(getAdapterPosition()).getEmail());
                   /* System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    System.out.println(mData.get(getAdapterPosition()).getImagen());
                    i.putExtra("img", mData.get(getAdapterPosition()).getImagen());*/
                    i.putExtra("saldo", mData.get(getAdapterPosition()).getSaldoActual());
                    i.putExtra("idEmpresa", mData.get(getAdapterPosition()).getId_empresa());



                    view.getContext().startActivity(i);



                    Toast.makeText(view.getContext(), "Proximamente", Toast.LENGTH_SHORT).show();
                }
            });

            btnMultarAlumno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createNewDialogAceptar(itemView, mData.get(getAdapterPosition()).getId_user());

                }
            });

            /*btnAcceder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MostrarPropiedadActivity.class);
                    i.putExtra("id", mData.get(getAdapterPosition()).getId_propiedades());//FIXME
                    i.putExtra("nombre", mData.get(getAdapterPosition()).getNombre());
                    i.putExtra("img1", mData.get(getAdapterPosition()).getImg1());
                    i.putExtra("precio", mData.get(getAdapterPosition()).getPrecio());
                    i.putExtra("habitaciones", mData.get(getAdapterPosition()).getHabitaciones());
                    i.putExtra("municipio", mData.get(getAdapterPosition()).getMunicipio());
                    i.putExtra("descripcion", mData.get(getAdapterPosition()).getDescripcion());
                    i.putExtra("metros_2", mData.get(getAdapterPosition()).getMetros_2());
                    i.putExtra("zona", mData.get(getAdapterPosition()).getZona());
                    i.putExtra("img2", mData.get(getAdapterPosition()).getImg2());
                    i.putExtra("img3", mData.get(getAdapterPosition()).getImg3());
                    i.putExtra("terraza", mData.get(getAdapterPosition()).getTerraza());
                    i.putExtra("pisicina", mData.get(getAdapterPosition()).getPiscina());
                    i.putExtra("aireAcondicionado", mData.get(getAdapterPosition()).getAireAcondicionado());
                    i.putExtra("parking", mData.get(getAdapterPosition()).getParking());
                    i.putExtra("tipo", mData.get(getAdapterPosition()).getTipo());
                    v.getContext().startActivity(i);
                }
            });*/


        }

        void bindData(final User item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdapterListaAlumnos.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        nombre = mData.get(position).getNombre();
        apellido = mData.get(position).getApellidos();
        rol = mData.get(position).getRol_juego();
        imagen = mData.get(position).getImagen();
        expediente = mData.get(position).getExpediente();
        expedienteString = String.valueOf(expediente);


        byte[] bytes= Base64.decode(imagen,Base64.DEFAULT);

        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);


        holder.ivImagenAlumno.setImageBitmap(bitmap);

        holder.tvNombreApellidoAlumno.setText(nombre+" "+apellido);
        holder.tvTipoRol.setText(rol);
        holder.tvExpedienteAlumno.setText(expedienteString);





    }
    public void setItems(List<User> items){ mData = items; }


    public void createNewDialogAceptar(View v, int idUser){//TODO POSIBLEMENTE TENGA QUE RECIBIR EL ID_USER O ID_EMPRESA
        dialogBuilder = new AlertDialog.Builder(v.getContext());
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popUpView = li.inflate(R.layout.popup_multa, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);

        etMulta = (EditText) popUpView.findViewById(R.id.etMulta);
        etMotivoMulta = (EditText) popUpView.findViewById(R.id.etMotivoMulta);
        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("Señale el motivo y la cantidad de la multa en €");



        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                token = cargarPreferencias();

                motivo = etMotivoMulta.getText().toString().trim();
                multa = Integer.parseInt(etMulta.getText().toString().trim());

                m = new Multa(idUser, motivo, multa);

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> si = apiService.multa(m,token);
                si.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        System.out.println(response.code());
                        if(response.body()!=null){
                            dialog.dismiss();
                            Toast.makeText(context, "Multa Realizada...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Intentelo de nuevo más tarde", Toast.LENGTH_SHORT).show();
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

    public String cargarPreferencias(){
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String tokenFinal = preferences.getString("token","No existe el token");

        return tokenFinal;


    }


}

