package com.abel.miequipo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.abel.miequipo.data.rankinJugadores.JugadorRankin;
import com.abel.miequipo.data.rankinJugadores.RepositorioRankin;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.List;


public class ViewModelRankinJugadores extends AndroidViewModel {

    private RepositorioRankin mRepository;
    private LiveData<List<JugadorRankin>> listaJugadores;
    private LiveData<List<JugadorRankin>> mAllJugadoresLocal;

    public ViewModelRankinJugadores(Application application) {
        super(application);
        mRepository = new RepositorioRankin(application);
        listaJugadores = mRepository.getAllJugadores();
    }

    public LiveData<List<JugadorRankin>> getAllJugadores() {
        return listaJugadores;
    }

    public LiveData<List<JugadorRankin>> getAllJugadoresTopPartidos() {
        listaJugadores= mRepository.getAllJugadoresTopPartidos();
        return listaJugadores;
    }

    public void insert(JugadorRankin jugadorEntitie) {
        mRepository.insert(jugadorEntitie);
    }

    public void addPartido(String partidosGanados, JugadorSeleccionado jugadorSeleccionado, List<JugadorRankin> mAllJugadoresLocal) {


        mRepository.addPartido(partidosGanados, jugadorSeleccionado, mAllJugadoresLocal);


    }

    public void addCampeonato(String campeonatoGanado, JugadorSeleccionado jugadorRankin, List<JugadorRankin> mAllJugadoresLocal) {

        mRepository.addCampeonato(campeonatoGanado, jugadorRankin, mAllJugadoresLocal);
    }

    public LiveData<List<JugadorRankin>> getJugador(String nombreJugador) {
        LiveData<List<JugadorRankin>> jugadorRankin = mRepository.getJugador(nombreJugador);

        return jugadorRankin;
    }

}