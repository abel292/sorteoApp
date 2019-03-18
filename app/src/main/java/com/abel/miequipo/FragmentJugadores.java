package com.abel.miequipo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterAllJugadores;
import com.abel.miequipo.adaptersRecycler.AdapterJugadoresSeleccionados;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.popups.Popup_agregar_jugador;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJugadores extends Fragment {

    ViewModelNuevoJugador viewModel;
    RecyclerView recyclerView,recyclerViewSeleccionados;
    private AutoCompleteTextView autoCompleteNombre;
    private Button buttonSortear, buttonMenos, buttonMas;
    private FloatingActionButton buttonCrearJug;
    private EditText editTextCuentaEquipos;
    private LinearLayout linearLayoutContenedorJugadores;
    private Popup_agregar_jugador popupAgregarJugador;

    public FragmentJugadores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_jugadores, container, false);

        final AdapterAllJugadores adapter = new AdapterAllJugadores(getContext());
        final AdapterJugadoresSeleccionados adapterSeleccionados = new AdapterJugadoresSeleccionados(getContext());

        /*adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Nombre:"+ adapter.mWords.get(recyclerView.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });*/
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);
        viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {
                adapter.setWords(jugadores);
            }
        });


        /*recyclerViewSeleccionados= v.findViewById(R.id.recyclerViewSeleccionados);
        recyclerView.setAdapter(adapterSeleccionados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModelJugadorSeleccionado = ViewModelProviders.of(getActivity()).get(ViewModelJugadorSeleccionado.class);
        viewModelJugadorSeleccionado.getAllJugadores().observe(getActivity(), new Observer<List<JugadorSeleccionado>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorSeleccionado> jugadores) {
                adapterSeleccionados.setWords(jugadores);
            }
        });*/

        configurarViews(v);



        return v;
    }

    private void configurarViews(View v){


        autoCompleteNombre= v.findViewById(R.id.autoCompleteNombre);
        buttonCrearJug= v.findViewById(R.id.buttonCrearJug);
        buttonMas = v.findViewById(R.id.buttonMas);
        buttonMenos = v.findViewById(R.id.buttonMenos);
        buttonSortear = v.findViewById(R.id.buttonSortear);
        editTextCuentaEquipos= v.findViewById(R.id.editTextCuentaEquipos);
        linearLayoutContenedorJugadores= v.findViewById(R.id.linearLayoutContenedorJugadores);

        buttonCrearJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPopupCrearJugador();
            }
        });

        configAutocomplete(autoCompleteNombre);

        popupAgregarJugador= new Popup_agregar_jugador(this.getActivity());
    }

    private void configAutocomplete(final AutoCompleteTextView autoCompleteTextView) {
        viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
                for (JugadorEntitie jugador: jugadores){
                    adapter.add(jugador.getNombre());
                }
                autoCompleteTextView.setAdapter(adapter);

            }
        });

    }


    private void abrirPopupCrearJugador(){
        popupAgregarJugador.openPopup();
    }

}
