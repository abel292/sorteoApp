package com.abel.miequipo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.allJugadores.DaoJugador;
import com.abel.miequipo.data.seleccionJugadores.DaoJugadoresSeleccionados;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;

@Database(entities = {JugadorEntitie.class, JugadorSeleccionado.class}, version = 5)
public abstract class MyDataBaseJugadores extends RoomDatabase  {

    public abstract DaoJugador daoJugador();
    public abstract DaoJugadoresSeleccionados daoJugadoresSeleccionados();
    private static volatile MyDataBaseJugadores INSTANCE;
    private static String DATABASE_NAME= "jugadores_database";


    public static MyDataBaseJugadores getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDataBaseJugadores.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDataBaseJugadores.class, DATABASE_NAME)
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
