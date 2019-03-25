package com.abel.miequipo;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.airbnb.lottie.LottieAnimationView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLoading extends Fragment {

    int current = 0;
    private static final String listaJugadores = "listaJugadoresMesclados";
    private static final String limitJugadores = "limitJugadores";
    LottieAnimationView animacionLoading;
    ArrayList<JugadorSeleccionado> jugadores;
    int limit;


    public FragmentLoading() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_loading, container, false);
        animacionLoading = v.findViewById(R.id.animacionLoading);
        animacionLoading.playAnimation();
        animacionLoading.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                current++;

                if (current > 3) {

                    //Toast.makeText(getContext(), "Aca termina la animacion", Toast.LENGTH_SHORT).show();
                    limit= getArguments().getInt(limitJugadores);
                    jugadores = getArguments().getParcelableArrayList(listaJugadores);

                    Bundle  bundle= new Bundle();
                    bundle.putParcelableArrayList(listaJugadores,jugadores);
                    bundle.putInt(limitJugadores,limit);
                    Navigation.findNavController(v).navigate(R.id.action_fragmentLoading_to_fragmentResults, bundle);
                    current = 0;
                }
            }


        });
        return v;
    }

}