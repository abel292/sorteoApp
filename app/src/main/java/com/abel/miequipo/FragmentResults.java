package com.abel.miequipo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterGridCancha;
import com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers;
import com.abel.miequipo.adaptersRecycler.AdapterListEquipos;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResults extends Fragment {

    ListView lvEquipos;

    private static final String listaJugadores = "listaJugadoresMesclados";
    private static final String limitJugadores = "limitJugadores" ;

    private GridView gridViewEquipo;
    ArrayList<JugadorSeleccionado> jugadores;
    int limit;

    public FragmentResults() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_results, container, false);

        lvEquipos= v.findViewById(R.id.lvEquipos);
        //gridViewEquipo= v.findViewById(R.id.gridViewEquipo);

        jugadores = getArguments().getParcelableArrayList(listaJugadores);
        limit= getArguments().getInt(limitJugadores);

        List<List<JugadorSeleccionado>> lista= formarEquipos(jugadores,limit);
        AdapterListEquipos adapter= new AdapterListEquipos(getContext(),lista,lvEquipos,limit);
        lvEquipos.setAdapter(adapter);

        //AdapterGridCancha adapterGrid= new AdapterGridCancha(getContext(),jugadores);
        //gridViewEquipo.setAdapter(adapterGrid);
        return v;
    }

    private List<List<JugadorSeleccionado>> formarEquipos(List<JugadorSeleccionado> jugadores, int formarEquiposDe) {

        int index= 0;
        int currentCount = 0;
        List<List<JugadorSeleccionado>> listasEquipos = new ArrayList<>();
        List<JugadorSeleccionado> equipo = new ArrayList<>();

        for (JugadorSeleccionado jugador : jugadores) {

            if (currentCount == formarEquiposDe && index != (jugadores.size()-1)) {
                listasEquipos.add(equipo);
                equipo = new ArrayList<>();
                currentCount = 0;
            }
            index++;
            if (currentCount != formarEquiposDe && index != (jugadores.size()-1) ){
                equipo.add(jugador);
                currentCount++;
            }

            if (index == (jugadores.size()-1)){
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