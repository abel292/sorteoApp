package com.abel.miequipo;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class ActivityResultSort extends AppCompatActivity {

    private static final String TAG = ActivityResultSort.class.getSimpleName();
    private static final String listaJugadores = "listaJugadoresMesclados";
    private static final String limitJugadores = "limitJugadores" ;

    ArrayList<JugadorSeleccionado> jugadores;
    private int formarEquiposDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_sort);
        getSupportActionBar().hide();


        if (getIntent().getExtras().containsKey(listaJugadores) && getIntent().getExtras().containsKey(limitJugadores)) {
            formarEquiposDe = getIntent().getExtras().getInt(limitJugadores);
            jugadores = getIntent().getExtras().getParcelableArrayList(listaJugadores);

            Bundle  bundle= new Bundle();
            //bundle.putString("hola","banola");
            bundle.putInt(limitJugadores,formarEquiposDe);
            bundle.putParcelableArrayList(listaJugadores,jugadores);


            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);  // Hostfragment
            NavInflater inflater = navHostFragment.getNavController().getNavInflater();
            NavGraph graph = inflater.inflate(R.navigation.nav_graph);
            graph.setDefaultArguments(bundle);
            graph.setStartDestination(R.id.fragmentLoading);

            navHostFragment.getNavController().setGraph(graph);
            navHostFragment.getNavController().getGraph().setDefaultArguments(getIntent().getExtras());

        }
    }

}

