package com.abel.miequipo.adaptersRecycler;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.List;


public class AdapterGridCancha extends BaseAdapter {
    private Context mContext;
    private List<JugadorSeleccionado> listaJugadores;

    public AdapterGridCancha(Context c, List<JugadorSeleccionado> listaJugadores) {
        mContext = c;
        this.listaJugadores= listaJugadores;
    }
    public int getCount() {
        return listaJugadores.size();
    }
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        JugadorSeleccionado currentJugador = listaJugadores.get(position);
        final TextView tvNameJugador = convertView.findViewById(R.id.tvNombre);
        tvNameJugador.setText(currentJugador.getNombre());
        return convertView;

       /* Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
            btn.setLayoutParams(new GridView.LayoutParams(150, 70));
            btn.setPadding(8, 10, 8, 10);
            btn.setText(listaJugadores.get(position).getNombre());
        }
        else {
            btn = (Button) convertView;
        }*/

    }
}