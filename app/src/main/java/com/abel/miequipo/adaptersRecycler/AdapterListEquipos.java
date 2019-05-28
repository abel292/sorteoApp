package com.abel.miequipo.adaptersRecycler;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.abel.miequipo.R;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;
import com.abel.miequipo.viewmodel.ViewModelRankinJugadores;

import java.util.List;

public class AdapterListEquipos extends BaseAdapter {

    private static final String TAG = com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers.class.getName();
    private List<List<JugadorSeleccionado>> jugadores;
    private Context mContext;
    private ListView lvAgregarJugadores;
    private LayoutInflater inflater = null;
    private int limit = 1;
    private int limitCampeonatos = 0;
    ViewModelRankinJugadores viewModelRankinJugadores;
    private List<JugadorSeleccionado> listaJugadores;
    List<JugadorRankin> mAllJugadoresLocal;
    private int height = 120;
    private int limitCampeonato;
    int currentWins;
    Fragment fragment;

    String[] equipos;


    public AdapterListEquipos(Fragment fragment, @NonNull List<List<JugadorSeleccionado>> lista, ListView listView, List<JugadorRankin> mAllJugadoresLocal, int limit, int limitCampeonato) {

        this.fragment = fragment;
        this.jugadores = lista;
        this.mContext = fragment.getContext();
        this.lvAgregarJugadores = listView;
        this.limit = limit;
        this.mAllJugadoresLocal = mAllJugadoresLocal;
        this.limitCampeonato = limitCampeonato;

        height = height * limit;

        viewModelRankinJugadores = ViewModelProviders.of(fragment).get(ViewModelRankinJugadores.class);

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

        final List<JugadorSeleccionado> listJugadores = jugadores.get(position);
        equipos = new String[jugadores.size()];


        ImageView imageViewMasUnPartido = convertView.findViewById(R.id.imageViewMasUnPartido);
        final TextView textViewWins = convertView.findViewById(R.id.textViewWins);


        for(int i=0;i<jugadores.size();i++){
            equipos[position] = textViewWins.getText().toString();
        }
        final TextView textViewNumEquipo = convertView.findViewById(R.id.textViewNumEquipo);
        ListView lvJugadores = convertView.findViewById(R.id.lvJugadores);
        ViewGroup.LayoutParams params = lvJugadores.getLayoutParams();
        params.height = height;
        lvJugadores.setLayoutParams(params);

        textViewNumEquipo.setText(String.valueOf("EQUIPO " + (position + 1)));
        imageViewMasUnPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWins = Integer.parseInt(textViewWins.getText().toString());
                //Toast.makeText(mContext, String.valueOf("EQUIPO " + (position + 1)) + " +1 WIN", Toast.LENGTH_SHORT).show();
                currentWins = currentWins + 1;

                textViewWins.setText(String.valueOf(currentWins));
                equipos[position] = textViewWins.getText().toString();

                if (currentWins == limitCampeonato) {
                    int count = 0;
                    for (JugadorSeleccionado jugadorSeleccionado : listJugadores) {
                        viewModelRankinJugadores.addCampeonato(String.valueOf("1"), jugadorSeleccionado, mAllJugadoresLocal);
                    }
                    for (List<JugadorSeleccionado> equiposPorSeparado : jugadores){
                        for (JugadorSeleccionado jugador : equiposPorSeparado){
                            viewModelRankinJugadores.addPartido(String.valueOf(equipos[count]), jugador, mAllJugadoresLocal);
                        }
                        count ++;

                    }

                    Toast.makeText(mContext, "Gano el " + String.valueOf("EQUIPO " + (position + 1)), Toast.LENGTH_SHORT).show();
                }

            }
        });

        for (List<JugadorSeleccionado> list : jugadores) {
            AdapterListJugadores adapter = new AdapterListJugadores(mContext, jugadores.get(position), lvJugadores);
            lvJugadores.setAdapter(adapter);
        }
        return convertView;
    }
}




