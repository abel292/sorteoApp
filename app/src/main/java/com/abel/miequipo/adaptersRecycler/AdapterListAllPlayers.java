package com.abel.miequipo.adaptersRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;

import java.util.List;

public class AdapterListAllPlayers extends BaseAdapter {

    private static final String TAG = AdapterListAllPlayers.class.getName();
    private List<JugadorEntitie> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;


    public AdapterListAllPlayers(Context context, List<JugadorEntitie> newPlayers, ListView lvAgregarJugadores) {

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

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        JugadorEntitie currentJugador = jugadores.get(position);
        final TextView newFriendShipName = convertView.findViewById(R.id.tvNombre);
        final View finalConvertView = convertView;

        if (!currentJugador.getNombre().isEmpty()) {
            newFriendShipName.setText(currentJugador.getNombre());
        } else {
            newFriendShipName.setText("sin nombre");
        }


        return convertView;
    }
}
