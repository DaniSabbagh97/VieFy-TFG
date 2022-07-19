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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdaptarPracticasCompradas extends RecyclerView.Adapter<ListAdaptarPracticasCompradas.ViewHolder> {
    private List<Compras> mData;
    private Context context;

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
        /*TextView tvNombrePractica, tvCoste, tvBeneficio;
        Button btnComprar, btnVerPDF;*/

        ViewHolder(View itemView){
            super(itemView);
            /*tvNombrePractica = itemView.findViewById(R.id.tvNombrePractica);
            tvCoste = itemView.findViewById(R.id.tvCoste);
            tvBeneficio = itemView.findViewById(R.id.tvBeneficio);
            btnComprar = itemView.findViewById(R.id.btnComprar);
            btnVerPDF = itemView.findViewById(R.id.btnVerPDF);
            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createNewDialogAceptar(itemView);
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
            });*/

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

        void bindData(final Compras item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdaptarPracticasCompradas.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

       /* nombre = mData.get(position).getNombrePractica();
        valorTotal = mData.get(position).getValorTotal();
        beneficio = mData.get(position).getBeneficio();



        holder.tvNombrePractica.setText(nombre);
        holder.tvBeneficio.setText("Beneficio: "+beneficio+" €");
        holder.tvCoste.setText("Coste "+ valorTotal+" €");
*/



    }
    public void setItems(List<Compras> items){ mData = items; }

}
