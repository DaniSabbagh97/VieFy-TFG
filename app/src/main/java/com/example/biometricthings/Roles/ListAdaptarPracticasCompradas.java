package com.example.biometricthings.Roles;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.biometricthings.Profesor.PrevisualizarPracticaActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Compras;
import com.example.biometricthings.model.PasarBytes;
import com.example.biometricthings.model.Practicas;
import com.example.biometricthings.remote.APIService;
import com.example.biometricthings.remote.RetroClass;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;

public class ListAdaptarPracticasCompradas extends RecyclerView.Adapter<ListAdaptarPracticasCompradas.ViewHolder> {
    private List<Compras> mData;
    private Context context;
    private byte[] pdf;
    private PasarBytes pb;
    private String nombre;
    private int beneficio, coste;

    public ListAdaptarPracticasCompradas(List<Compras> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdaptarPracticasCompradas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_compras, parent, false);

        return new ListAdaptarPracticasCompradas.ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombrePractica, tvCostePractica, tvBeneficioPractica;
        Button btnVerPractica;

        ViewHolder(View itemView){
            super(itemView);
            tvNombrePractica = itemView.findViewById(R.id.tvNombrePractica);
            tvCostePractica = itemView.findViewById(R.id.tvCostePractica);
            tvBeneficioPractica = itemView.findViewById(R.id.tvBeneficioPractica);
            btnVerPractica = itemView.findViewById(R.id.btnVerPractica);

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


        }

        void bindData(final Compras item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdaptarPracticasCompradas.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        nombre = mData.get(position).getPractica().getNombrePractica();
        coste = mData.get(position).getPractica().getValorTotal();
        beneficio = mData.get(position).getPractica().getBeneficio();

        holder.tvNombrePractica.setText(nombre);
        holder.tvCostePractica.setText("Coste: "+coste+" €");
        holder.tvBeneficioPractica.setText("Beneficio "+beneficio+" €");



    }
    public void setItems(List<Compras> items){ mData = items; }

}
