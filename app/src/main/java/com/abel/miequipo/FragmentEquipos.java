package com.abel.miequipo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abel.miequipo.adapters.CustomListView;

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

    private CustomListView adapter;


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

        configurarViews();

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

        configAutocomplete(autoCompleteNombre);
    }


    private void configAutocomplete(final AutoCompleteTextView autoCompleteTextView) {
        // cargamos lista completa de jugadores para autocompletar
        /*final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        autoComplete.add("abel");
        autoComplete.add("ivan");
        autoComplete.add("santi");
        autoCompleteTextView.setAdapter(autoComplete);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),autoCompleteTextView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });*/

        List<String> lista= new ArrayList<>();
        lista.add("abel");
        lista.add("ivan");
        lista.add("sofia");


        adapter = new CustomListView(getContext(), R.layout.item_busqueda_jugador, lista);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void cargarAutocomplete(){


    }
}
