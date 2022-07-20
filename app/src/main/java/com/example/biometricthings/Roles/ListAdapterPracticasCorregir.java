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

import com.example.biometricthings.Profesor.PrevisualizarPracticaActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.Correcion;
import com.example.biometricthings.model.Multa;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapterPracticasCorregir extends RecyclerView.Adapter<ListAdapterPracticasCorregir.ViewHolder> {

    private List<Compras> mData;
    private Context context;
    private byte[] pdf;
    private byte[] pdfCorregir;
    private PasarBytes pb;
    private String nombre;
    private int beneficio, coste;
    private float nota;
    private Correcion correcion;

    private String token;
    private int idCompra;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button btnSi, btnNo;
    private TextView tvInfo;
    private EditText etNota;

    public ListAdapterPracticasCorregir(List<Compras> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdapterPracticasCorregir.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_corregir_practica, parent, false);

        return new ListAdapterPracticasCorregir.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePractica, tvCoste, tvBeneficio;
        Button btnVerPractica, btnPuntuar, btnCorregir;

        ViewHolder(View itemView){
            super(itemView);
            tvNombrePractica = itemView.findViewById(R.id.tvNombrePractica);
            tvCoste = itemView.findViewById(R.id.tvCoste);
            tvBeneficio = itemView.findViewById(R.id.tvBeneficio);
            btnVerPractica = itemView.findViewById(R.id.btnVerPractica);
            btnPuntuar = itemView.findViewById(R.id.btnPuntuar);
            btnCorregir = itemView.findViewById(R.id.btnCorregir);

            btnVerPractica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    pdf = mData.get(getAdapterPosition()).getPractica().getPdf();
                    pb = new PasarBytes(pdf);

                    Intent i = new Intent(view.getContext(), PrevisualizarPracticaActivity.class);
                    i.putExtra("pb",pb);
                    view.getContext().startActivity(i);
                }
            });

            btnCorregir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    pdfCorregir = mData.get(getAdapterPosition()).getEntrega();
                    pb = new PasarBytes(pdfCorregir);

                    Intent i = new Intent(view.getContext(), PrevisualizarPracticaActivity.class);
                    i.putExtra("pb",pb);
                    view.getContext().startActivity(i);
                }
            });

            btnPuntuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idCompra = mData.get(getAdapterPosition()).getId_compra();
                    createNewDialogAceptar(view, idCompra);

                }
            });


        }

        void bindData(final Compras item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdapterPracticasCorregir.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        nombre = mData.get(position).getPractica().getNombrePractica();
        coste = mData.get(position).getPractica().getValorTotal();
        beneficio = mData.get(position).getPractica().getBeneficio();

        holder.tvNombrePractica.setText(nombre);
        holder.tvCoste.setText("Coste: "+coste+" €");
        holder.tvBeneficio.setText("Beneficio "+beneficio+" €");



    }
    public void setItems(List<Compras> items){ mData = items; }



    public void createNewDialogAceptar(View v, int id){//TODO POSIBLEMENTE TENGA QUE RECIBIR EL ID_USER O ID_EMPRESA
        dialogBuilder = new AlertDialog.Builder(v.getContext());
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popUpView = li.inflate(R.layout.popup_puntua, null);
        tvInfo = (TextView) popUpView.findViewById(R.id.tvInfo);

        etNota = (EditText) popUpView.findViewById(R.id.etNota);

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

                nota = Float.parseFloat(etNota.getText().toString().trim());
                correcion = new Correcion(id, nota);

                final APIService apiService = RetroClass.getAPIService();

                Call<Boolean> si = apiService.corregirPractica(correcion,token);
                si.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.code()==200){
                            if(response.body()!=null){
                                if(response.body()){
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(view.getContext(), "No se pudo subir la nota", Toast.LENGTH_SHORT).show();
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
