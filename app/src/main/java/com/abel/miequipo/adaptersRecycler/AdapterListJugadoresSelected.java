package com.abel.miequipo.adaptersRecycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;

import java.util.List;

public class AdapterListJugadoresSelected extends BaseAdapter {

    private static final String TAG = AdapterListAllPlayers.class.getName();
    private List<JugadorSeleccionado> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;

    public AdapterListJugadoresSelected(Context context, List<JugadorSeleccionado> newPlayers, ListView lvAgregarJugadores) {

        this.jugadores = newPlayers;
        this.mContext = context;
        this.lvAgregarJugadores = lvAgregarJugadores;

    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        JugadorSeleccionado currentJugador = jugadores.get(position);
        final TextView tvNameJugador = convertView.findViewById(R.id.tvNombre);
        tvNameJugador.setText(currentJugador.getNombre());
        return convertView;
    }
}
