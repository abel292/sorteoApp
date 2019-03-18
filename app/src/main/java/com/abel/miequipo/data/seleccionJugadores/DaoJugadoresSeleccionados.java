package com.abel.miequipo.data.seleccionJugadores;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface DaoJugadoresSeleccionados {
    @Query("SELECT * FROM jugadores_seleccionados")
    LiveData<List<JugadorSeleccionado>> getAllJugador();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugador(JugadorSeleccionado jugador);

    @Query("DELETE FROM jugadores_seleccionados")
    void deleteAllUsers();
}

