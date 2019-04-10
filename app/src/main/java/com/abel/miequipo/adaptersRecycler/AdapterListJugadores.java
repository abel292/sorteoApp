package com.abel.miequipo.adaptersRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.miequipo.R;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterListJugadores extends BaseAdapter {

    private static final String TAG = com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers.class.getName();
    private List<JugadorSeleccionado> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;


    public AdapterListJugadores(Context context, List<JugadorSeleccionado> lista, ListView listView) {

        this.jugadores = lista;
        this.mContext = context;
        this.lvAgregarJugadores = listView;

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
        convertView = inflater.inflate(R.layout.item_list_jugadores, parent, false);
        TextView textViewNombreJugador = convertView.findViewById(R.id.textViewNombreJugador);
        textViewNombreJugador.setText(String.valueOf(jugadores.get(position).getNombre()));

        CircleImageView imagenJugador = convertView.findViewById(R.id.imagenJugador);
        Glide.with(mContext)
                .load(jugadores.get(position).getImagen())
                .override(50, 50) // resizes the image to 100x200 pixels but does not respect aspect ratio
                .into(imagenJugador);

        return convertView;
    }
}

