package com.abel.miequipo;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.abel.miequipo.adaptersRecycler.AdapterListAllPlayers;
import com.abel.miequipo.adaptersRecycler.AdapterPlayersSelected;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.popups.Popup_agregar_jugador;
import com.abel.miequipo.viewmodel.ViewModelJugadorSeleccionado;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJugadores extends Fragment {

    ViewModelNuevoJugador viewModel;
    ViewModelJugadorSeleccionado viewModelJugadorSeleccionado;
    ListView recyclerView, recyclerViewSeleccionados;

    private static String TAG = "TAGG";
    private AutoCompleteTextView autoCompleteNombre;
    private Button buttonMenos, buttonMas, buttonSiguiente;
    private FloatingActionButton buttonCrearJug;
    private EditText editTextCuentaEquipos;
    private ImageButton imageButtonLimpiarLista;
    private LinearLayout linearLayoutContenedorJugadores;
    private Popup_agregar_jugador popupAgregarJugador;
    private LiveData<List<JugadorSeleccionado>> jugadoresSeleccionados;
    private AdapterListAllPlayers adapter;
    final List<JugadorEntitie> lista = new ArrayList<>() ;

    private int textlength = 0;
    private List<JugadorEntitie> array_sort = new ArrayList<>();


    public FragmentJugadores() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_jugadores, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerViewSeleccionados = v.findViewById(R.id.recyclerViewSeleccionados);

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelNuevoJugador.class);
        viewModelJugadorSeleccionado = ViewModelProviders.of(getActivity()).get(ViewModelJugadorSeleccionado.class);

        viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {
                // adapter.setWords(jugadores);
                lista.clear();
                adapter= new AdapterListAllPlayers(getContext(), jugadores, recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setClickable(true);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String nombreClicked= String.valueOf(jugadores.get(position).getNombre());
                        Toast.makeText(getContext(), String.valueOf(jugadores.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModel.deleteJugador(jugadores.get(position));
                        viewModelJugadorSeleccionado.insert(new JugadorSeleccionado(jugadores.get(position).getNombre(), ""));
                        array_sort.clear();
                        lista.clear();
                        //recyclerView.removeViewAt(position);
                        //adapter.notifyDataSetChanged();

                    }
                });

                for (JugadorEntitie jugador:viewModel.getAllJugadores().getValue() ){
                    lista.add(jugador);
                }

            }
        });

        viewModelJugadorSeleccionado.getAllJugadores().observe(getActivity(), new Observer<List<JugadorSeleccionado>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorSeleccionado> jugadores) {
                //  adapterSeleccionados.setWords(jugadores);

                final AdapterPlayersSelected adapter = new AdapterPlayersSelected(getContext(), jugadores, recyclerView);
                recyclerViewSeleccionados.setAdapter(adapter);
                recyclerViewSeleccionados.setClickable(true);
                recyclerViewSeleccionados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "Eliminando a " + String.valueOf(jugadores.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModel.insert(new JugadorEntitie(jugadores.get(position).getNombre(), jugadores.get(position).getImagen()));
                        viewModelJugadorSeleccionado.deleteJugador(jugadores.get(position));
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });



        configurarViews(v);


        return v;
    }

    private void configurarViews(View v) {

        autoCompleteNombre = v.findViewById(R.id.autoCompleteNombre);
        buttonCrearJug = v.findViewById(R.id.buttonCrearJug);
        buttonMas = v.findViewById(R.id.buttonMas);
        buttonMenos = v.findViewById(R.id.buttonMenos);
        buttonSiguiente = v.findViewById(R.id.buttonSiguiente);
        editTextCuentaEquipos = v.findViewById(R.id.editTextCuentaEquipos);
        linearLayoutContenedorJugadores = v.findViewById(R.id.linearLayoutContenedorJugadores);
        imageButtonLimpiarLista = v.findViewById(R.id.imageButtonLimpiarLista);

        buttonCrearJug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPopupCrearJugador();
            }
        });

        imageButtonLimpiarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { limpiarListaSeleccionados();
            }
        });

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGoToActivitySorteo= new Intent( getContext(), ActivitySorteo.class);
                startActivity(intentGoToActivitySorteo);
                //sortearEquipos(jugadoresSeleccionados);
            }
        });
        configAutocomplete(autoCompleteNombre);
        popupAgregarJugador = new Popup_agregar_jugador(this.getActivity());
    }

    private void limpiarListaSeleccionados() {
        Toast.makeText(getContext(), "Vaciando lista...", Toast.LENGTH_SHORT).show();
        viewModel.moveAllToPlayerList(viewModelJugadorSeleccionado.getAllJugadores());
    }



    private void configAutocomplete(final AutoCompleteTextView autoCompleteTextView) {

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Abstract Method of TextWatcher Interface.
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = autoCompleteTextView.getText().length();
                array_sort.clear();

                for (int i = 0; i < lista.size(); i++) {
                    if (textlength <= lista.get(i).getNombre().length()) {
                        if (autoCompleteTextView.getText().toString().equalsIgnoreCase((String) lista.get(i).getNombre().subSequence(0, textlength))) {
                            array_sort.add(lista.get(i));
                        }
                    }
                }
                recyclerView.setAdapter(new AdapterListAllPlayers(getContext(), array_sort, recyclerView));
                recyclerView.setClickable(true);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String nombreClicked= String.valueOf(array_sort.get(position).getNombre());
                        Toast.makeText(getContext(), String.valueOf(array_sort.get(position).getNombre()), Toast.LENGTH_SHORT).show();
                        viewModel.deleteJugador(array_sort.get(position));
                        viewModelJugadorSeleccionado.insert(new JugadorSeleccionado(array_sort.get(position).getNombre(), ""));
                        array_sort.clear();
                        lista.clear();
                        //recyclerView.removeViewAt(position);
                        //adapter.notifyDataSetChanged();

                    }
                });

            }
        });

        /*viewModel.getAllJugadores().observe(getActivity(), new Observer<List<JugadorEntitie>>() {
            @Override
            public void onChanged(@Nullable final List<JugadorEntitie> jugadores) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
                for (JugadorEntitie jugador : jugadores) {
                    adapter.add(jugador.getNombre());
                }
                autoCompleteTextView.setAdapter(adapter);

            }
        });*/

    }

    private void abrirPopupCrearJugador() {
        popupAgregarJugador.openPopup();
    }

}
