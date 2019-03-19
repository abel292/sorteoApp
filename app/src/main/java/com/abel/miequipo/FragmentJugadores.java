package com.abel.miequipo;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterJugadoresSeleccionados;
import com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers;
import com.abel.miequipo.adaptersRecycler.AdapterPlayersSelected;
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
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    ListView recyclerView,recyclerViewSeleccionados;
    private AutoCompleteTextView autoCompleteNombre;
    private Button buttonSortear, buttonMenos, buttonMas;
    private FloatingActionButton buttonCrearJug;
    private EditText editTextCuentaEquipos;
    private ImageButton imageButtonLimpiarLista;
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

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerViewSeleccionados= v.findViewById(R.id.recyclerViewSeleccionados);

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);
        viewModelJugadorSeleccionado = ViewModelProviders.of(getActivity()).get(ViewModelJugadorSeleccionado.class);

        viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {
               // adapter.setWords(jugadores);
                final AdapterListAllPlayers adapter = new AdapterListAllPlayers(getContext(),jugadores,recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setClickable(true);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Toast.makeText(getContext(), String.valueOf(jugadores.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModelJugadorSeleccionado.insert(new JugadorSeleccionado(jugadores.get(position).getNombre(),""));
                        //recyclerView.removeViewAt(position);
                        //adapter.notifyDataSetChanged();

                    }
                });

            }
        });

        viewModelJugadorSeleccionado.getAllJugadores().observe(getActivity(), new Observer<List<JugadorSeleccionado>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorSeleccionado> jugadores) {
              //  adapterSeleccionados.setWords(jugadores);

                final AdapterPlayersSelected adapter = new AdapterPlayersSelected(getContext(),jugadores,recyclerView);
                recyclerViewSeleccionados.setAdapter(adapter);
                recyclerViewSeleccionados.setClickable(true);
                recyclerViewSeleccionados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "Eliminando a "+String.valueOf(jugadores.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModelJugadorSeleccionado.deleteJugador(jugadores.get(position));
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });

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
        imageButtonLimpiarLista= v.findViewById(R.id.imageButtonLimpiarLista);

        buttonCrearJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPopupCrearJugador();
            }
        });

        imageButtonLimpiarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { limpiarListaSeleccionados(); }
        });

        configAutocomplete(autoCompleteNombre);

        popupAgregarJugador= new Popup_agregar_jugador(this.getActivity());
    }

    private void limpiarListaSeleccionados() {
        Toast.makeText(getContext(), "Vaciando lista...", Toast.LENGTH_SHORT).show();
        viewModelJugadorSeleccionado.deleteAllJugadores();
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
