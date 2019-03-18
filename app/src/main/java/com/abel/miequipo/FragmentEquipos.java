package com.abel.miequipo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.popups.Popup_agregar_jugador;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEquipos extends Fragment {

    private AutoCompleteTextView autoCompleteNombre;
    private Button buttonCrearJugador,buttonSortear, buttonMenos, buttonMas;
    private EditText editTextCuentaEquipos;
    private LinearLayout linearLayoutContenedorJugadores;
    private Popup_agregar_jugador popupAgregarJugador;

    ViewModelNuevoJugador viewModel;
    List<JugadorEntitie> lista= new ArrayList<>();

    int currentValue= 0;
    public FragmentEquipos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_equipos, container, false);

        autoCompleteNombre= v.findViewById(R.id.autoCompleteNombre);
        buttonCrearJugador= v.findViewById(R.id.buttonCrearJugador);
        buttonMas = v.findViewById(R.id.buttonMas);
        buttonMenos = v.findViewById(R.id.buttonMenos);
        buttonSortear = v.findViewById(R.id.buttonSortear);
        editTextCuentaEquipos= v.findViewById(R.id.editTextCuentaEquipos);
        linearLayoutContenedorJugadores= v.findViewById(R.id.linearLayoutContenedorJugadores);

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);

        configurarViews();

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);
        viewModel.getAllJugadores().observe(this, new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> words) {
                // Update the cached copy of the words in the adapter.
                configAutocomplete(autoCompleteNombre);
            }
        });


        return v;
    }

    private void configurarViews(){

        buttonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue= Integer.parseInt(editTextCuentaEquipos.getText().toString())+1;
                editTextCuentaEquipos.setText(String.valueOf(currentValue));
            }
        });

        buttonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue= Integer.parseInt(editTextCuentaEquipos.getText().toString())-1;
                editTextCuentaEquipos.setText(String.valueOf(currentValue));
            }
        });

        buttonCrearJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPopupCrearJugador();
            }
        });

        configAutocomplete(autoCompleteNombre);

        popupAgregarJugador= new Popup_agregar_jugador(this.getActivity());
    }

    private void abrirPopupCrearJugador(){
        popupAgregarJugador.openPopup();
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

    private void deleteAllJugadores(){
        viewModel.deleteAllJugadores();
    }
}
