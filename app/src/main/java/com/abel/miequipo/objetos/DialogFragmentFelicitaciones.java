package com.abel.miequipo.objetos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abel.miequipo.R;

public class DialogFragmentFelicitaciones {
    Context context;
    Activity activity;

    public DialogFragmentFelicitaciones(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void openDialogSelectUser(String equipowin, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.dialog_felicitaciones, null);



        final TextView equipoGanador = (TextView) subView.findViewById(R.id.textViewEquipo);
        final TextView nameGanadores = (TextView) subView.findViewById(R.id.textViewNameGanadores);

        nameGanadores.setText(message);
        equipoGanador.setText(equipowin);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(subView);

        AlertDialog alertDialog = builder.create();

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                activity.finish();
            }
        });


        builder.show();
    }
}
