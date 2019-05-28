package com.abel.miequipo.data.rankinJugadores;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;

import java.util.List;

@Dao
public interface DaoJugadorRankin {
    @Query("SELECT * FROM table_rankin ORDER BY campeonatosGanados DESC")
    LiveData<List<JugadorRankin>> getAllJugadoresTopCampeonatos();

    @Query("SELECT * FROM table_rankin ORDER BY partidosGanados DESC")
    LiveData<List<JugadorRankin>> getAllJugadoresTopPartidos();

    @Query("SELECT * FROM table_rankin WHERE nombre = :nombreJugador")
    LiveData<List<JugadorRankin>> getJugador( String nombreJugador);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJugador(JugadorRankin jugador);

    @Query("DELETE FROM table_rankin")
    void deleteAllUsers();

    @Query("DELETE FROM table_rankin WHERE nombre = :nombreJugador")
    void deleteJugadorSeleccionado(String nombreJugador);

    @Query("UPDATE table_rankin SET partidosGanados =:partidosGanados WHERE nombre = :nombreJugador")
    void addPartido(String partidosGanados, String nombreJugador);

    @Query("UPDATE table_rankin SET campeonatosGanados =:campeonatosGanado WHERE nombre = :nombreJugador")
    void addCampeonato(String campeonatosGanado, String nombreJugador);
}