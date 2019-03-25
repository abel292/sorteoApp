package com.abel.miequipo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.allJugadores.RepositorioDatos;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.List;

public class ViewModelNuevoJugador extends AndroidViewModel {

    private RepositorioDatos mRepository;
    private LiveData<List<JugadorEntitie>> listaJugadores;

    public ViewModelNuevoJugador(Application application) {
        super(application);
        mRepository = new RepositorioDatos(application);
        listaJugadores = mRepository.getAllJugadores();
    }

    public LiveData<List<JugadorEntitie>> getAllJugadores() {
        return listaJugadores;
    }

    public void insert(JugadorEntitie jugadorEntitie) {
        mRepository.insert(jugadorEntitie);
    }

    public void deleteAllJugadores() {
        mRepository.deleteAllJugadores();
    }

    public void deleteJugador(JugadorEntitie jugador) {
        mRepository.deleteJugador(jugador);
    }


    public void moveAllToPlayerList(LiveData<List<JugadorSeleccionado>> mJugadoresSelected) {
        mRepository.moveAllToPlayerList(mJugadoresSelected);
    }
}