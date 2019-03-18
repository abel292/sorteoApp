package com.abel.miequipo.adaptersRecycler;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBusquedaJugador extends RecyclerView.Adapter<AdapterBusquedaJugador.MyViewHolder> {

    public List<JugadorEntitie> lista_tarjetas;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        CircleImageView ivIcon;


        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
            ivIcon = v.findViewById(R.id.imageView);
            textViewNombre = v.findViewById(R.id.text1);

        }
    }


    public AdapterBusquedaJugador(List<JugadorEntitie> lista_tarjetas) {
        this.lista_tarjetas = lista_tarjetas;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterBusquedaJugador.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busqueda_jugador, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textViewNombre.setText(lista_tarjetas.get(position).getNombre());

//getCompressedBitmap(lista_tarjetas.get(position).getProfile_image())

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lista_tarjetas.size();

    }

}