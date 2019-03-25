package com.abel.miequipo.adaptersRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.miequipo.R;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;

import java.util.List;

public class AdapterListEquipos extends BaseAdapter {

    private static final String TAG = com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers.class.getName();
    private List<List<JugadorSeleccionado>> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    private int limit=1;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    private int height = 120;



    public AdapterListEquipos(Context context, List<List<JugadorSeleccionado>> lista, ListView listView, int limit) {

        this.jugadores = lista;
        this.mContext = context;
        this.lvAgregarJugadores = listView;
        this.limit= limit;

        height= height* limit;

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
        convertView = inflater.inflate(R.layout.item_equipo, parent, false);

        TextView textViewNumEquipo = convertView.findViewById(R.id.textViewNumEquipo);
        ListView lvJugadores= convertView.findViewById(R.id.lvJugadores);
        ViewGroup.LayoutParams params = lvJugadores.getLayoutParams();
        params.height = height;
        lvJugadores.setLayoutParams(params);

        textViewNumEquipo.setText(String.valueOf("EQUIPO "+(position+1)));

        for (List<JugadorSeleccionado> list: jugadores){
            AdapterListJugadores adapter= new AdapterListJugadores(mContext,jugadores.get(position),lvJugadores);
            lvJugadores.setAdapter(adapter);
        }
        return convertView;
    }
}



