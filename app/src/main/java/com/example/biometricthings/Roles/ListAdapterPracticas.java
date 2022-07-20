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
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.model.SolicitudAceptada;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterPracticas extends RecyclerView.Adapter<ListAdapterPracticas.ViewHolder> {
    private List<Practicas> mData;
    private Context context;
    private String nombre;
    private String token;
    private int valorTotal, beneficio;
    private PasarBytes pb;

    byte[] pdf;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button btnSi, btnNo;
    private TextView tvInfo;
    private Practicas practicas;


    public ListAdapterPracticas(List<Practicas> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdapterPracticas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_practicas, parent, false);

        return new ListAdapterPracticas.ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePractica, tvCoste, tvBeneficio;
        Button btnComprar, btnVerPDF;

        ViewHolder(View itemView){
            super(itemView);
            tvNombrePractica = itemView.findViewById(R.id.tvNombrePractica);
            tvCoste = itemView.findViewById(R.id.tvCoste);
            tvBeneficio = itemView.findViewById(R.id.tvBeneficio);
            btnComprar = itemView.findViewById(R.id.btnComprar);
            btnVerPDF = itemView.findViewById(R.id.btnVerPDF);
            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_practica = mData.get(getAdapterPosition()).getId_practica();
                    createNewDialogAceptar(itemView, id_practica);
                }
            });
            btnVerPDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdf = mData.get(getAdapterPosition()).getPdf();
                    pb = new PasarBytes(pdf);

                    Intent i = new Intent(view.getContext(), PrevisualizarPracticaActivity.class);
                    i.putExtra("pb",pb);
                    view.getContext().startActivity(i);
                }
            });
//TODO TOCAR AQUÍ PARA ENVIAR A LA BBDD LOS DATOS DE LA COMPRA
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

        void bindData(final Practicas item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdapterPracticas.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        nombre = mData.get(position).getNombrePractica();
        valorTotal = mData.get(position).getValorTotal();
        beneficio = mData.get(position).getBeneficio();



        holder.tvNombrePractica.setText(nombre);
        holder.tvBeneficio.setText("Beneficio: "+beneficio+" €");
        holder.tvCoste.setText("Coste "+ valorTotal+" €");




    }
    public void setItems(List<Practicas> items){ mData = items; }


    public void createNewDialogAceptar(View v, int id){
        dialogBuilder = new AlertDialog.Builder(v.getContext());
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popUpView = li.inflate(R.layout.popup_confirmacion_propiedad, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);

        btnSi = (Button) popUpView.findViewById(R.id.btnSi);
        btnNo = (Button) popUpView.findViewById(R.id.btnNo);

        tvInfo.setText("¿Estás Seguro de Comprar la Actividad?");
        token = cargarPreferencias();
        dialogBuilder.setView(popUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                practicas = new Practicas(id);

                final APIService apiService = RetroClass.getAPIService();
                Call<Boolean> p = apiService.crearCompra(practicas,token);

                p.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.code()==200){
                            if(response.body()!=null){
                                if(response.body()){
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "No tienes suficiente dinero para comprar la práctica o ya la compraste", Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                Toast.makeText(view.getContext(), "Intentelo más tarde...", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(view.getContext(), "El servidor envío una respuesta de: "+response.code()+" ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });

//TODO COMPRAR LA PRACTICA
             /*   salarioInt = Integer.parseInt((etSalario.getText().toString().trim()));

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
*/

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
