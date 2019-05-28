package com.abel.miequipo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abel.miequipo.adaptersRecycler.AdapterRankinJugadores;
import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.viewmodel.ViewModelRankinJugadores;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRankin extends Fragment {


    ViewModelRankinJugadores viewModelRankinJugadores;

    List<JugadorRankin> lista = new ArrayList<>();
    RecyclerView recyclerView;
    LineChart grafica;

    ImageView imageView, imageView2,imageView3;
    TextView textViewNombreJugador ,textViewNombreJugador2 ,textViewNombreJugador3;
    TextView textViewPartidos, textViewPartidos2, textViewPartidos3;
    TextView textViewCampeonatos, textViewCampeonatos2, textViewCampeonatos3;

    int currentValue = 0;

    public FragmentRankin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rankin, container, false);

        binding(v);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        viewModelRankinJugadores = ViewModelProviders.of(this.getActivity()).get(ViewModelRankinJugadores.class);


        /////////////////////////////////////////////////////estadisticas////////////////////////////////////////////
        grafica = v.findViewById(R.id.grafica);
        Description description = new Description();
        description.setText("Hola banola");
        grafica.setDescription(description);

        LineDataSet lineDataSet = new LineDataSet(getDataSet(), "Mi linea wacho");
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new ReportChartXAxisValuesFormatter(getXAxisValues()));

        grafica.setData(lineData);
        grafica.animateXY(3000, 3000);
        grafica.invalidate();

        /////////////////////////////////////////////////////estadisticas////////////////////////////////////////////


        viewModelRankinJugadores.getAllJugadores().observe(getActivity(), new Observer<List<JugadorRankin>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorRankin> jugadores) {

                JugadorRankin jugadorUno= jugadores.get(0);
                JugadorRankin jugadorDos= jugadores.get(1);
                JugadorRankin jugadorTres= jugadores.get(2);

                //TOP 1
                textViewCampeonatos.setText(jugadorUno.getCampeonatosGanados());
                textViewNombreJugador.setText(jugadorUno.getNombre());
                textViewPartidos.setText(jugadorUno.getPartidosGanados());

                //TOP 2
                textViewCampeonatos2.setText(jugadorDos.getCampeonatosGanados());
                textViewNombreJugador2.setText(jugadorDos.getNombre());
                textViewPartidos2.setText(jugadorDos.getPartidosGanados());

                //TOP 3
                textViewCampeonatos3.setText(jugadorTres.getCampeonatosGanados());
                textViewNombreJugador3.setText(jugadorTres.getNombre());
                textViewPartidos3.setText(jugadorTres.getPartidosGanados());
                // Toast.makeText(getContext(),"RANKIN: "+ jugadores.get(0).getNombre(), Toast.LENGTH_SHORT).show();

                final AdapterRankinJugadores adapter = new AdapterRankinJugadores(getContext(), jugadores);
                recyclerView.setAdapter(adapter);
            }
        });


        viewModelRankinJugadores.getAllJugadoresTopPartidos().observe(getActivity(), new Observer<List<JugadorRankin>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorRankin> jugadores) {

                JugadorRankin jugadorUno= jugadores.get(0);
                JugadorRankin jugadorDos= jugadores.get(1);
                JugadorRankin jugadorTres= jugadores.get(2);

                //TOP 1
                textViewCampeonatos.setText(jugadorUno.getCampeonatosGanados());
                textViewNombreJugador.setText(jugadorUno.getNombre());
                textViewPartidos.setText(jugadorUno.getPartidosGanados());

                //TOP 2
                textViewCampeonatos2.setText(jugadorDos.getCampeonatosGanados());
                textViewNombreJugador2.setText(jugadorDos.getNombre());
                textViewPartidos2.setText(jugadorDos.getPartidosGanados());

                //TOP 3
                textViewCampeonatos3.setText(jugadorTres.getCampeonatosGanados());
                textViewNombreJugador3.setText(jugadorTres.getNombre());
                textViewPartidos3.setText(jugadorTres.getPartidosGanados());
                // Toast.makeText(getContext(),"RANKIN: "+ jugadores.get(0).getNombre(), Toast.LENGTH_SHORT).show();

                final AdapterRankinJugadores adapter = new AdapterRankinJugadores(getContext(), jugadores);

                recyclerView.setAdapter(adapter);


            }
        });

        return v;
    }

    private void binding (View v){

        textViewCampeonatos= v.findViewById(R.id.textViewCampeonatos);
        textViewNombreJugador= v.findViewById(R.id.textViewNombreJugador);
        textViewPartidos= v.findViewById(R.id.textViewPartidos);

        //TOP 2
        textViewCampeonatos2= v.findViewById(R.id.textViewCampeonatos2);
        textViewNombreJugador2= v.findViewById(R.id.textViewNombreJugador2);
        textViewPartidos2= v.findViewById(R.id.textViewPartidos2);

        //TOP 3
        textViewCampeonatos3= v.findViewById(R.id.textViewCampeonatos3);
        textViewNombreJugador3= v.findViewById(R.id.textViewNombreJugador3);
        textViewPartidos3= v.findViewById(R.id.textViewPartidos3);
    }

    /////////////////////////////////////////////estadisticas////////////////////////////////////////////
    private List<Entry> getDataSet() {

        List<Entry> listaEntries = new ArrayList<Entry>();

        listaEntries.add(new Entry(4f, 0));
        listaEntries.add(new Entry(3f, 1));
        listaEntries.add(new Entry(5f, 2));
        listaEntries.add(new Entry(6f, 4));
        listaEntries.add(new Entry(18f, 5));
        listaEntries.add(new Entry(27f, 6));
        listaEntries.add(new Entry(20f, 7));


        return listaEntries;
    }

    private List<String> getXAxisValues() {

        List<String> xAxis = new ArrayList<>();

        xAxis.add("lun");
        xAxis.add("mar");
        xAxis.add("mier");
        xAxis.add("jue");
        xAxis.add("vie");
        xAxis.add("sab");
        xAxis.add("dom");


        return xAxis;
    }

    private class ReportChartXAxisValuesFormatter extends ValueFormatter implements IValueFormatter {

        private List<String> labels;

        public ReportChartXAxisValuesFormatter(List<String> labels) {

            this.labels = labels;
        }


        @Override
        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {

            try {
                int index = (int) v;
                return this.labels.get(index);

            } catch (Exception e) {
                return null;

            }
        }
    }

    /////////////////////////////////////////////////////estadisticas////////////////////////////////////////////


}
