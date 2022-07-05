package com.example.biometricthings.Profesor;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biometricthings.HomeActivity;
import com.example.biometricthings.Propiedades.ListAdapter;
import com.example.biometricthings.Propiedades.MostrarPropiedadActivity;
import com.example.biometricthings.R;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Propiedades;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterClases extends RecyclerView.Adapter<ListAdapterClases.ViewHolder> {

    private List<Clase> mData;
    private Context context;
    private String clase;
    private int alumnos;


    public ListAdapterClases(List<Clase> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdapterClases.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_clases, parent, false);

        return new ListAdapterClases.ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClase,tvAlumno;
        Button btnAcceder;

        ViewHolder(View itemView){
            super(itemView);
            tvClase = itemView.findViewById(R.id.tvClase);
            tvAlumno = itemView.findViewById(R.id.tvAlumnos);
            btnAcceder = itemView.findViewById(R.id.btnAcceder);
            btnAcceder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), HomeActivity.class);
                    i.putExtra("idClase", mData.get(getAdapterPosition()).getId_clase());
                    view.getContext().startActivity(i);
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

        void bindData(final Clase item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdapterClases.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        clase = mData.get(position).getNombre();
        alumnos = mData.get(position).getNumero_de_usos();



        holder.tvClase.setText(clase);
        holder.tvAlumno.setText(alumnos+"");




    }
    public void setItems(List<Clase> items){ mData = items; }


}

