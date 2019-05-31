package com.abel.miequipo;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private static final String limitJugadores = "limitJugadores" ;
    private static final String limitCampeonatos = "limitCampeonato" ;

    LottieAnimationView animacionLoading;
    ArrayList<JugadorSeleccionado> jugadores;
    int limit;
    int limitCampeonato;


    public FragmentLoading() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_loading, container, false);

        if (getArguments().containsKey(limitJugadores)){
            limit= getArguments().getInt(limitJugadores);
            limitCampeonato= getArguments().getInt(limitCampeonatos);
            jugadores = getArguments().getParcelableArrayList(listaJugadores);
            Log.e("esnull",String.valueOf(jugadores.size()));
        }else if(!getArguments().containsKey(listaJugadores)){
            Toast.makeText(getContext(), "no existe key lista", Toast.LENGTH_SHORT).show();
        }else if(!getArguments().containsKey(limitJugadores)){
            Toast.makeText(getContext(), "no existe key limit", Toast.LENGTH_SHORT).show();
        }else if(!getArguments().containsKey(limitCampeonatos)){
            Toast.makeText(getContext(), "no existe key limit campeonato", Toast.LENGTH_SHORT).show();
        }

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
                    //Toast.makeText(getContext(), "limit fragmen animation "+String.valueOf(limitCampeonato), Toast.LENGTH_SHORT).show();

                    Log.e("esnull",String.valueOf(jugadores.size()));
                    Bundle  bundle= new Bundle();
                    bundle.putParcelableArrayList(listaJugadores,jugadores);
                    bundle.putInt(limitJugadores,limit);
                    bundle.putInt(limitCampeonatos,limitCampeonato);
                    Navigation.findNavController(v).navigate(R.id.action_fragmentLoading_to_fragmentResults, bundle);
                    current = 0;
                }
            }


        });
        return v;
    }

}
