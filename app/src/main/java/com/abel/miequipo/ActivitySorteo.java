package com.abel.miequipo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterGridCancha;
import com.abel.miequipo.adaptersRecycler.AdapterListJugadoresSelected;
import com.abel.miequipo.adaptersRecycler.AdapterPlayersSelected;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;

import java.util.ArrayList;
import java.util.List;

public class ActivitySorteo extends AppCompatActivity {

    private static final String limitCampeonato = "limitCampeonato";
    private static final String limitJugadores = "limitJugadores";
    private static final String listaJugadores = "listaJugadoresMesclados";
    private Button buttonSortear, buttonMenos, buttonMas,  buttonMenos1, buttonMas1;
    private EditText editTextCuentaEquipos, editTextCuentaEquipos1;
    int currentValue = 0;
    int currentValue1 = 0;

    private LiveData<List<JugadorSeleccionado>> jugadoresSeleccionados;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    private AdapterGridCancha adapter;
    private GridView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteo);
        getSupportActionBar().hide();

        listView= findViewById(R.id.gridView);

        viewModelJugadorSeleccionado = ViewModelProviders.of(this).get(ViewModelJugadorSeleccionado.class);
        viewModelJugadorSeleccionado.getAllJugadores().observe(ActivitySorteo.this, new Observer<List<JugadorSeleccionado>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorSeleccionado> jugadores) {
                adapter = new AdapterGridCancha (getBaseContext(),jugadores);
                listView.setAdapter(adapter);
            }
        });

        jugadoresSeleccionados = viewModelJugadorSeleccionado.getAllJugadores();
        configView();

    }

    private void configView() {
        buttonMas = findViewById(R.id.buttonMas);
        buttonMenos = findViewById(R.id.buttonMenos);

        buttonMas1 = findViewById(R.id.buttonMas1);
        buttonMenos1 = findViewById(R.id.buttonMenos1);

        buttonSortear = findViewById(R.id.buttonSortear);
        editTextCuentaEquipos = findViewById(R.id.editTextCuentaEquipos);
        editTextCuentaEquipos1 = findViewById(R.id.editTextCuentaEquipos1);

        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue = Integer.parseInt(editTextCuentaEquipos.getText().toString()) + 1;
                editTextCuentaEquipos.setText(String.valueOf(currentValue));
            }
        });

        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue = Integer.parseInt(editTextCuentaEquipos.getText().toString()) - 1;
                editTextCuentaEquipos.setText(String.valueOf(currentValue));
            }
        });

        buttonMas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue1 = Integer.parseInt(editTextCuentaEquipos1.getText().toString()) + 1;
                editTextCuentaEquipos1.setText(String.valueOf(currentValue1));
            }
        });

        buttonMenos1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue1 = Integer.parseInt(editTextCuentaEquipos1.getText().toString()) - 1;
                editTextCuentaEquipos1.setText(String.valueOf(currentValue1));
            }
        });

        buttonSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int limit = Integer.parseInt(String.valueOf(editTextCuentaEquipos.getText()));
                int limitCampeonato = Integer.parseInt(String.valueOf(editTextCuentaEquipos1.getText()));
                List<JugadorSeleccionado> jugadoresMesclados = sortearEquipos(jugadoresSeleccionados);
                irAnimacionSorteo(jugadoresMesclados, limit,limitCampeonato);
            }
        });
    }

    private List<JugadorSeleccionado> sortearEquipos(LiveData<List<JugadorSeleccionado>> listaJugadoresSeleccionados) {
        //Toast.makeText(this, "Sorteando lista...", Toast.LENGTH_SHORT).show();
        List<JugadorSeleccionado> listaJugadoresMesclados = new ArrayList<>();
        List<JugadorSeleccionado> lista = new ArrayList<>();
        for (JugadorSeleccionado jugador : listaJugadoresSeleccionados.getValue()) {
            lista.add(jugador);
        }
        if (lista.size() >= 1) {
            while (lista.size() >= 2) {
                int max_aleatorio = lista.size();
                int numeroAleatorio = (int) (Math.random() * max_aleatorio);

                JugadorSeleccionado jugadorSorteado = lista.get(numeroAleatorio);
                listaJugadoresMesclados.add(jugadorSorteado);
                lista.remove(lista.get(numeroAleatorio));
            }
            for (JugadorSeleccionado jugador : lista) {
                listaJugadoresMesclados.add(jugador);
            }
            for (JugadorSeleccionado jugador : listaJugadoresMesclados) {
                Log.e("orden:: ", jugador.getNombre());
            }
        }
        return listaJugadoresMesclados;
    }


    private void irAnimacionSorteo(List<JugadorSeleccionado> jugadoresMesclados, int limit, int limitCampeonatos) {
        ArrayList<JugadorSeleccionado> listaCasteada = new ArrayList<>();
        for (JugadorSeleccionado jugador : jugadoresMesclados) {
            listaCasteada.add(jugador);
        }
        Intent intent = new Intent(this, ActivityResultSort.class);
        intent.putExtra(limitJugadores, limit);
        intent.putExtra(limitCampeonato, limitCampeonatos);
        intent.putExtra(listaJugadores, listaCasteada);
        startActivity(intent);
    }

}
