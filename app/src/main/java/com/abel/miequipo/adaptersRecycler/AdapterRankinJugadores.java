package com.abel.miequipo.adaptersRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.abel.miequipo.R;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.bumptech.glide.Glide;


import java.util.Collections;
import java.util.List;

public class AdapterRankinJugadores extends RecyclerView.Adapter<AdapterRankinJugadores.WordViewHolder> {
    public String nombreJugador;
    Context context;
    View.OnClickListener listener;
    public LayoutInflater mInflater;
    public List<JugadorRankin> listaJugadores = Collections.emptyList(); // Cached copy of words


    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombreJugador, textViewGoles, textViewPartidos, textViewCampeonatos;
        private ImageView imagenJugador;

        private WordViewHolder(View itemView) {
            super(itemView);
            textViewNombreJugador = itemView.findViewById(R.id.textViewNombreJugador);
            textViewGoles = itemView.findViewById(R.id.textViewGoles);
            textViewPartidos = itemView.findViewById(R.id.textViewPartidosGanados);
            textViewCampeonatos = itemView.findViewById(R.id.textViewCampeonatos);
            imagenJugador = itemView.findViewById(R.id.imagenJugador);

        }
    }


    public AdapterRankinJugadores(@NonNull Context context, List<JugadorRankin> listaJugadores) {
        if (context != null) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
            this.listaJugadores = listaJugadores;
        }


    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_jugador_rankin, parent, false);

//        itemView.setOnClickListener(this);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, final int position) {
        final JugadorRankin current = listaJugadores.get(position);


        holder.textViewNombreJugador.setText(current.getNombre());
        holder.textViewGoles.setText(String.valueOf(current.getGoles()));
        holder.textViewPartidos.setText(String.valueOf(current.getPartidosGanados()));
        holder.textViewCampeonatos.setText(String.valueOf(current.getCampeonatosGanados()));


        if (current.getImagen().isEmpty()
                || current.getImagen().equalsIgnoreCase("")
                || current.getImagen() == null
                || current.getImagen().length() < 1
                || current.getImagen().equalsIgnoreCase("sin foto")) {
            holder.imagenJugador.setImageResource(R.drawable.jugador);

            Log.d("allJugadores", current.getNombre() + " partidos: " + current.getPartidosGanados());

        } else {
            //Imagen.setPic(image,currentJugador.getImagen(),2);
            Glide.with(context)
                    .load(current.getImagen())
                    .override(50, 50) // resizes the image to 100x200 pixels but does not respect aspect ratio
                    .into(holder.imagenJugador);
            //Toast.makeText(mContext,"entre con glide", Toast.LENGTH_SHORT).show();
        }
       

    }

    public void setListaJugadores(List<JugadorRankin> words) {
        listaJugadores = words;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaJugadores.size());
    }

    @Override
    public int getItemCount() {
        return listaJugadores.size();
    }

    /*public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {

        if (listener != null) {
            listener.onClick(v);
        }
    }*/
}
