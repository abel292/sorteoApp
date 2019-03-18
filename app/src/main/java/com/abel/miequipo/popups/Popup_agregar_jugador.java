package com.abel.miequipo.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Popup_agregar_jugador extends DialogFragment {

    Activity context;
    List<EditText> listaEditText = new ArrayList<>();
    EditText editTextNombre;
    JugadorEntitie jugadorEntitie;
    ViewModelNuevoJugador viewModel;

    int current = 0;

    public Popup_agregar_jugador(Activity context) {
        this.context = context;

    }

    public void openPopup() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.popup_agregar_jugador, null);
        final EditText editTextNombre = subView.findViewById(R.id.editTextNombre);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                viewModel= new ViewModelNuevoJugador(context.getApplication());
                JugadorEntitie jugador = new JugadorEntitie(editTextNombre.getText().toString(),"foto1");
                viewModel.insert(jugador);

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Accion cancelada", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();


    }

    public void crearJugador(JugadorEntitie jugadorEntitie) {

        this.jugadorEntitie = jugadorEntitie;
    }
}
