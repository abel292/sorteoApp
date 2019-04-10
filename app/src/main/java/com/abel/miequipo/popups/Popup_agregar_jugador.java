package com.abel.miequipo.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abel.miequipo.R;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.objetos.Imagen;
import com.abel.miequipo.viewmodel.ViewModelNuevoJugador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class Popup_agregar_jugador extends DialogFragment {

    Activity activity;
    Context context;
    List<EditText> listaEditText = new ArrayList<>();
    EditText editTextNombre;
    JugadorEntitie jugadorEntitie;
    ViewModelNuevoJugador viewModel;
    ImageView perfil;
    int current = 0;
    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 4;

    String path;

    public Popup_agregar_jugador(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

    }

    public void openPopup() {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View subView = inflater.inflate(R.layout.popup_agregar_jugador, null);
        final EditText editTextNombre = subView.findViewById(R.id.editTextNombre);
        perfil = subView.findViewById(R.id.imageViewPerfil);


        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                }

            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel = new ViewModelNuevoJugador(activity.getApplication());
                JugadorEntitie jugador = new JugadorEntitie(editTextNombre.getText().toString(), "foto1");
                viewModel.insert(jugador);
            }
        });


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "Accion cancelada", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


}