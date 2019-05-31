package com.abel.miequipo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterGridCancha;
import com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers;
import com.abel.miequipo.adaptersRecycler.AdapterListEquipos;
import com.abel.miequipo.adaptersRecycler.AdapterPlayersSelected;
import com.abel.miequipo.adaptersRecycler.AdapterRankinJugadores;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelRankinJugadores;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResults extends Fragment {

    ListView lvEquipos;

    private static final String listaJugadores = "listaJugadoresMesclados";
    private static final String limitJugadores = "limitJugadores";



    private GridView gridViewEquipo;
    ArrayList<JugadorSeleccionado> jugadoresSeleccionados;
    int limit;
    int limitCampeonato;

    ViewModelRankinJugadores viewModelRankinJugadores;




    public FragmentResults() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_results, container, false);

        lvEquipos = v.findViewById(R.id.lvEquipos);
        //gridViewEquipo= v.findViewById(R.id.gridViewEquipo);

        jugadoresSeleccionados = getArguments().getParcelableArrayList(listaJugadores);
        limit = getArguments().getInt(limitJugadores);
        limitCampeonato = getArguments().getInt("limitCampeonato");
        //Toast.makeText(getContext(), "limit campeonato fragment result: "+limitCampeonato, Toast.LENGTH_SHORT).show();
        final List<List<JugadorSeleccionado>> lista = formarEquipos(jugadoresSeleccionados, limit);

        viewModelRankinJugadores = ViewModelProviders.of(getActivity()).get(ViewModelRankinJugadores.class);

        viewModelRankinJugadores.getAllJugadores().observe(getActivity(), new Observer<List<JugadorRankin>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorRankin> jugadores) {

            }
        });

        viewModelRankinJugadores.getAllJugadores().observe(getActivity(), new Observer<List<JugadorRankin>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorRankin> jugadores) {

                if (jugadores.size()>0){
                    AdapterListEquipos adapter =  new AdapterListEquipos(FragmentResults.this,lista,lvEquipos,jugadores,limit,limitCampeonato);
                    lvEquipos.setAdapter(adapter);
                }else {
                    Toast.makeText(getContext(), "ERROR tamaño lista: "+String.valueOf(jugadores.size()), Toast.LENGTH_SHORT).show();

                }

            }
        });


        //AdapterGridCancha adapterGrid= new AdapterGridCancha(getContext(),jugadores);
        //gridViewEquipo.setAdapter(adapterGrid);
        return v;
    }

    private List<List<JugadorSeleccionado>> formarEquipos(List<JugadorSeleccionado> jugadores, int formarEquiposDe) {

        int index = 0;
        int currentCount = 0;
        List<List<JugadorSeleccionado>> listasEquipos = new ArrayList<>();
        List<JugadorSeleccionado> equipo = new ArrayList<>();

        for (JugadorSeleccionado jugador : jugadores) {

            if (currentCount == formarEquiposDe && index != (jugadores.size() - 1)) {
                listasEquipos.add(equipo);
                equipo = new ArrayList<>();
                currentCount = 0;
            }
            index++;
            if (currentCount != formarEquiposDe && index != (jugadores.size() - 1)) {
                equipo.add(jugador);
                currentCount++;
            }

            if (index == (jugadores.size() - 1)) {
                equipo.add(jugador);
                listasEquipos.add(equipo);
            }

        }
        return listasEquipos;
    }

}


/*List<List> listaEquipos= new ArrayList<>();

        List<JugadorSeleccionado> lista= new ArrayList<>();
        JugadorSeleccionado j1= new JugadorSeleccionado("abel","foto");
        JugadorSeleccionado j2= new JugadorSeleccionado("ivan","foto");
        JugadorSeleccionado j3= new JugadorSeleccionado("sofia","foto");
        lista.add(j1);
        lista.add(j2);
        lista.add(j3);

        List<JugadorSeleccionado> lista1= new ArrayList<>();
        JugadorSeleccionado j4= new JugadorSeleccionado("kikan","foto");
        JugadorSeleccionado j5= new JugadorSeleccionado("fabio","foto");
        JugadorSeleccionado j6= new JugadorSeleccionado("papicho","foto");
        lista1.add(j4);
        lista1.add(j5);
        lista1.add(j6);


        List<JugadorSeleccionado> lista2= new ArrayList<>();
        JugadorSeleccionado j7= new JugadorSeleccionado("maxi","foto");
        JugadorSeleccionado j8= new JugadorSeleccionado("maciel","foto");
        JugadorSeleccionado j9= new JugadorSeleccionado("tity","foto");
        lista2.add(j7);
        lista2.add(j8);
        lista2.add(j9);

        List<JugadorSeleccionado> lista3= new ArrayList<>();
        JugadorSeleccionado j10= new JugadorSeleccionado("maxi","foto");
        JugadorSeleccionado j11= new JugadorSeleccionado("maciel","foto");
        JugadorSeleccionado j12= new JugadorSeleccionado("tity","foto");
        lista3.add(j10);
        lista3.add(j11);
        lista3.add(j12);

        listaEquipos.add(lista);
        listaEquipos.add(lista1);
        listaEquipos.add(lista2);
        listaEquipos.add(lista3);
*/