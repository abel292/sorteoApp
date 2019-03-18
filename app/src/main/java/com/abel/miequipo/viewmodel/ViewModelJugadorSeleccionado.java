package com.abel.miequipo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.allJugadores.RepositorioDatos;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados;

import java.util.List;

public class ViewModelJugadorSeleccionado extends AndroidViewModel {

    private RepositorioJugadoresSeleccionados mRepository;
    private LiveData<List<JugadorSeleccionado>> listaJugadores;

    public ViewModelJugadorSeleccionado(Application application) {
        super(application);
        mRepository = new RepositorioJugadoresSeleccionados(application);
        listaJugadores = mRepository.getAllJugadores();
    }

    public LiveData<List<JugadorSeleccionado>> getAllJugadores() {
        return listaJugadores;
    }

    public void insert(JugadorSeleccionado JugadorSeleccionado) {
        mRepository.insert(JugadorSeleccionado);
    }

    public void deleteAllJugadores() {
        mRepository.deleteAllJugadores();
    }


}
