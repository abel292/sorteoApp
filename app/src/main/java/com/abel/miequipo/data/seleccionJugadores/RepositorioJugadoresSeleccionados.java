package com.abel.miequipo.data.seleccionJugadores;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.abel.miequipo.data.DataBase;
import com.abel.miequipo.data.allJugadores.DaoJugador;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;

import java.util.List;

public class RepositorioJugadoresSeleccionados {

    private DaoJugador mDaoJugadorSeleccionado;
    private LiveData<List<JugadorSeleccionado>> mAllJugadores;

    public RepositorioJugadoresSeleccionados(Application application) {
        DataBase db = DataBase.getDatabase(application);
        mDaoJugadorSeleccionado = db.daoJugador();
       //mAllJugadores = mDaoJugadorSeleccionado.getAllJugadorSeleccionados();
    }

    public LiveData<List<JugadorSeleccionado>> getAllJugadores() {
        return mAllJugadores;
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void insert(JugadorSeleccionado jugador) {
        new com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados.insertAsyncTask(mDaoJugadorSeleccionado).execute(jugador);
    }

    public void deleteAllJugadores() {
        new com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados.deleteAllAsyncTask(mDaoJugadorSeleccionado);
        new com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados.deleteAllAsyncTask(mDaoJugadorSeleccionado);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class insertAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {

        private DaoJugador mAsyncTaskDao;

        insertAsyncTask(DaoJugador dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {
            //mAsyncTaskDao.insertJugadorSeleccionado(params[0]);
            return null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class deleteAllAsyncTask extends AsyncTask<JugadorEntitie, Void, Void> {

        private DaoJugador mAsyncTaskDao;

        deleteAllAsyncTask(DaoJugador dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JugadorEntitie... params) {
            mAsyncTaskDao.deleteAllUsers();
            return null;
        }
    }


}