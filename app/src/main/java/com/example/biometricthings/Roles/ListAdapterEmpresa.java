package com.example.biometricthings.Roles;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biometricthings.R;
import com.example.biometricthings.model.Empresa;
import com.example.biometricthings.model.Empresaa;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterEmpresa extends RecyclerView.Adapter<ListAdapterEmpresa.ViewHolder>{
    private List<Empresaa> mData;
    private Context context;
    private String nombre, slogan, anuncio;

    public ListAdapterEmpresa(List<Empresaa> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ListAdapterEmpresa.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_empresas, parent, false);

        return new ListAdapterEmpresa.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNombreEmpresa, tvSlogan;
        Button btnSeleccionar;

        ViewHolder(View itemView){
            super(itemView);
            tvNombreEmpresa = itemView.findViewById(R.id.tvEmpresa);
            tvSlogan = itemView.findViewById(R.id.tvSlogan);
            btnSeleccionar = itemView.findViewById(R.id.btnSolicitar);

            btnSeleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MostrarEmpresaIndividualActivity.class);
                    i.putExtra("id", mData.get(getAdapterPosition()).getId_empresa());
                    i.putExtra("nombre", mData.get(getAdapterPosition()).getNombre());
                    i.putExtra("slogan", mData.get(getAdapterPosition()).getSlogan());
                    i.putExtra("anuncio", mData.get(getAdapterPosition()).getAnuncio());
                    v.getContext().startActivity(i);
                }
            });


        }

        void bindData(final Empresaa item){

        }



    }
    @Override
    public void onBindViewHolder(final ListAdapterEmpresa.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));


        nombre = mData.get(position).getNombre();
       // anuncio = mData.get(position).getAnuncio();
        slogan = mData.get(position).getSlogan();

        holder.tvNombreEmpresa.setText(nombre);
        holder.tvSlogan.setText(slogan);



    }


}
