package com.abel.miequipo.data.allJugadores;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

import java.util.List;

@Dao
public interface DaoJugador {
    @Query("SELECT * FROM jugadores")
    LiveData<List<JugadorEntitie>> getAllJugador();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugador(JugadorEntitie jugador);

    @Query("DELETE FROM jugadores")
    void deleteAllUsers();

   /* @Query("SELECT * FROM jugadores_seleccionados")
    LiveData<List<JugadorSeleccionado>> getAllJugadorSeleccionados();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugadorSeleccionado(JugadorSeleccionado jugador);

    @Query("DELETE FROM jugadores_seleccionados")
    void deleteAllUsersSeleccionados();*/
}