package com.abel.miequipo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.allJugadores.DaoJugador;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

@Database(entities = {JugadorEntitie.class },version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract DaoJugador daoJugador();

    private static volatile DataBase INSTANCE;

    public static DataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "jugadores_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
