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
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.objetos.Imagen;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPlayersSelected extends BaseAdapter {

    private static final String TAG = AdapterListAllPlayers.class.getName();
    private List<JugadorSeleccionado> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;

    public AdapterPlayersSelected(Context context, List<JugadorSeleccionado> newPlayers, ListView lvAgregarJugadores) {

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
        JugadorSeleccionado currentJugador = jugadores.get(position);
        final TextView newFriendShipName = convertView.findViewById(R.id.tvNombre);
        CircleImageView image = convertView.findViewById(R.id.imagenJugador);

        if (currentJugador.getImagen().isEmpty()
                || currentJugador.getImagen().equalsIgnoreCase("")
                || currentJugador.getImagen()==null
                || currentJugador.getImagen().length()==0){
            image.setImageResource(R.drawable.jugador);
        }else {
            //Imagen.setPic(image,currentJugador.getImagen(),2);
            //Imagen.setPic(image,currentJugador.getImagen(),2);
            Glide.with(mContext)
                    .load(currentJugador.getImagen())
                    .override(50, 50) // resizes the image to 100x200 pixels but does not respect aspect ratio
                    .into(image);
        }

        if (!currentJugador.getNombre().isEmpty()) {
            newFriendShipName.setText(currentJugador.getNombre());
        } else {
            newFriendShipName.setText("sin nombre");
        }


        return convertView;
    }
}

