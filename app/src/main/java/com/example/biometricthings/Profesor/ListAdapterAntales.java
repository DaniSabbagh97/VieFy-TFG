package com.example.biometricthings.Profesor;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import com.example.biometricthings.model.Antales;
import com.example.biometricthings.model.Clase;
import com.example.biometricthings.model.Propiedades;
import com.example.biometricthings.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterAntales extends RecyclerView.Adapter<ListAdapterAntales.ViewHolder> {

    private List<Antales> mData;
    private Context context;
    private String nombre, apellido, tipo, rol;
    private String imagen;
    private User usr;




    public ListAdapterAntales(List<Antales> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemCount(){ return mData.size();}

    @NonNull
    @Override
    public ListAdapterAntales.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_element_antales, parent, false);

        return new ListAdapterAntales.ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreAlumno,tvTipoAntales, tvTipoRolJuego;
        ImageView ivImagenAlumnoAntales;
        Button btnCorregir;

        ViewHolder(View itemView){
            super(itemView);
            tvNombreAlumno = itemView.findViewById(R.id.tvNombreAlumno);
            tvTipoAntales = itemView.findViewById(R.id.tvTipoAntales);
            tvTipoRolJuego = itemView.findViewById(R.id.tvTipoRolJuego);

            ivImagenAlumnoAntales = itemView.findViewById(R.id.ivImagenAlumnoAntales);
            btnCorregir = itemView.findViewById(R.id.btnCorregir);
            btnCorregir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), MostrarAntalesIndividual.class);
                    i.putExtra("id", mData.get(getAdapterPosition()).getId_antales());
                    i.putExtra("nombre", mData.get(getAdapterPosition()).getUser().getNombre());
                    i.putExtra("apellido", mData.get(getAdapterPosition()).getUser().getApellidos());
                    i.putExtra("imagen", mData.get(getAdapterPosition()).getUser().getImagen());
                    i.putExtra("rol", mData.get(getAdapterPosition()).getUser().getRol_juego());
                    i.putExtra("tipoAntal", mData.get(getAdapterPosition()).getTipo());
                    i.putExtra("saldoReal", mData.get(getAdapterPosition()).getUser().getSaldoActual());
                    i.putExtra("saldoPorAlumno", mData.get(getAdapterPosition()).getCuenta());
                    i.putExtra("BeneficioPorAlumno", mData.get(getAdapterPosition()).getBeneficio());
                   // i.putExtra("BeneficioReal", mData.get(getAdapterPosition()).getUser().getImagen());
                    i.putExtra("Mensaje", mData.get(getAdapterPosition()).getMensaje());
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

        void bindData(final Antales item){

        }


    }
    @Override
    public void onBindViewHolder(final ListAdapterAntales.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));

        nombre = mData.get(position).getUser().getNombre();//TODO GET NOMBRE
        apellido = mData.get(position).getUser().getApellidos();//TODO GET APELLIDO
        tipo = mData.get(position).getTipo();
        imagen = mData.get(position).getUser().getImagen();
        //rol = mData.get(position).getUsr().getRol_juego();//TODO GET ROL
        //;//TODO GET IMAGEN
       /* usr = new User();
        usr = mData.get(position).getUser();
        nombre = usr.getNombre();*/
        System.out.println("NOMBREEEEEEEEEEEE");
        System.out.println(nombre);



        holder.tvTipoAntales.setText(tipo);
        holder.tvNombreAlumno.setText(nombre+" "+apellido);
        holder.tvTipoRolJuego.setText(rol);


        byte[] bytes= Base64.decode(imagen,Base64.DEFAULT);

        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        holder.ivImagenAlumnoAntales.setImageBitmap(bitmap);




    }
    public void setItems(List<Antales> items){ mData = items; }


}

