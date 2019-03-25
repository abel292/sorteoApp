package com.abel.miequipo.adaptersRecycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.Collections;
import java.util.List;

public class AdapterJugadoresSeleccionados  extends RecyclerView.Adapter<AdapterJugadoresSeleccionados.WordViewHolder> {

        class WordViewHolder extends RecyclerView.ViewHolder {
            private TextView tvNombre;

            private WordViewHolder(View itemView) {
                super(itemView);
                tvNombre = itemView.findViewById(R.id.tvNombre);
                tvNombre.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        tvNombre.setBackgroundColor(R.color.colorPrimary);
                    }
                });
            }
        }

        private final LayoutInflater mInflater;
        private List<JugadorSeleccionado> mWords = Collections.emptyList(); // Cached copy of words

        public AdapterJugadoresSeleccionados(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public AdapterJugadoresSeleccionados.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_jugador_seleccionado, parent, false);
            return new WordViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(com.abel.miequipo.adaptersRecycler.AdapterJugadoresSeleccionados.WordViewHolder holder, int position) {
            JugadorSeleccionado current = mWords.get(position);
            holder.tvNombre.setText(current.getNombre());
        }

        public void setWords(List<JugadorSeleccionado> words) {
            mWords = words;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }
    }
