package com.example.biometricthings.Propiedades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biometricthings.R;
import com.example.biometricthings.model.Propiedades;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Propiedades> mData;
   // private LayoutInflater mInflater;
    private Context context;
    //private Activity activity;
    private String nombre;
    private String precio;
    private String habitaciones;
    private String img1;
    private String municipio;
    private String descripcion;
    private String Baños;
    private String metros_2;
    private String zona;
    private String img2;
    private String img3;
    private int terraza;
    private int pisicina;
    private int aireAcondicionado;
    private int parking;

    //private ImageView ivImage;

   // private OnListAdapterListener olal = ((OnListAdapterListener) context);

    public ListAdapter(List<Propiedades> mData, Context context) {
        this.mData = mData;
        this.context = context;

        /*try{

            this.olal = ((OnListAdapterListener) context);

        }catch(ClassCastException e){

            throw new ClassCastException(e.getMessage());

        }*/
    }



    @Override
    public int getItemCount(){ return mData.size();}

   /* @Override
    public  ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element, parent, false);

        return new ListAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvNombre,tvPrecio,tvHabitaciones, tvMunicipio;
        Button btnVer;

        ViewHolder(View itemView){
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImagen);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvHabitaciones = itemView.findViewById(R.id.tvHabitaciones);
            tvMunicipio = itemView.findViewById(R.id.tvMunicipio);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnVer.setOnClickListener(new View.OnClickListener() {
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
                    v.getContext().startActivity(i);
                }
            });


        }

        void bindData(final Propiedades item){

        }


    }


    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        img1 = mData.get(position).getImg1();
        nombre = mData.get(position).getNombre();
        precio = String.valueOf(mData.get(position).getPrecio());
        habitaciones = String.valueOf(mData.get(position).getHabitaciones());
        municipio = mData.get(position).getMunicipio();
        descripcion = mData.get(position).getDescripcion();
        metros_2 = String.valueOf(mData.get(position).getMetros_2());
        zona = mData.get(position).getZona();
        img2 = mData.get(position).getImg2();
        img3 = mData.get(position).getImg3();
        terraza = mData.get(position).getTerraza();
        pisicina = mData.get(position).getPiscina();
        aireAcondicionado = mData.get(position).getAireAcondicionado();
        parking = mData.get(position).getParking();

        Picasso.with(context).load(img1).into(holder.ivImage);
        holder.tvNombre.setText(nombre);
        double precioDouble = Double.parseDouble(precio);
        double precioDoubleFinal;
        String precioFinal = "";
        if(precioDouble<10){

            precioDoubleFinal = precioDouble*500;//TODO FIXME NO TIENE SENTIDO PORQUE EN LA BBDD SIGUE SIENDO EL VALOR < 10
            precioFinal = String.valueOf(precioDoubleFinal);
            holder.tvPrecio.setText(precioFinal+"€/mes");


        }else{
            precioFinal = precio;
            holder.tvPrecio.setText(precioFinal+"€/mes");
        }



        holder.tvHabitaciones.setText("Habitaciones: "+habitaciones);
        holder.tvMunicipio.setText(municipio);




    }
    public void setItems(List<Propiedades> items){ mData = items; }



    /*public interface OnListAdapterListener{
        public void onListAdaptaerListener(Intent intent);
    }*/

}
