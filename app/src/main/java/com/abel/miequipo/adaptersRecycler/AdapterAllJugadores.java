package com.abel.miequipo.adaptersRecycler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;

import java.util.Collections;
import java.util.List;


public class AdapterAllJugadores extends RecyclerView.Adapter<AdapterAllJugadores.WordViewHolder>  {
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    public String nombreJugador;
    View.OnClickListener listener;
    public final LayoutInflater mInflater;
    public List<JugadorEntitie> mWords = Collections.emptyList(); // Cached copy of words


    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;

        private WordViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }
    }

    

    public AdapterAllJugadores(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
//        itemView.setOnClickListener(this);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {
        final JugadorEntitie current = mWords.get(position);
        final String nombreJugadorClikeado = current.getNombre();
        holder.tvNombre.setText(current.getNombre());
        /*holder.tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreJugador=nombreJugadorClikeado;
            }
        });*/
    }

    public void setWords(List<JugadorEntitie> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
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


