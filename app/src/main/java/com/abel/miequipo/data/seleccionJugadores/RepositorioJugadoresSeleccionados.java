package com.abel.miequipo.data.seleccionJugadores;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.abel.miequipo.data.MyDataBaseJugadores;
import com.abel.miequipo.data.allJugadores.DaoJugador;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;

import java.util.List;

public class RepositorioJugadoresSeleccionados {

    private DaoJugadoresSeleccionados mDaoJugadorSeleccionado;
    private LiveData<List<JugadorSeleccionado>> mAllJugadores;

    public RepositorioJugadoresSeleccionados(Application application) {
        MyDataBaseJugadores db = MyDataBaseJugadores.getDatabase(application);
        mDaoJugadorSeleccionado = db.daoJugadoresSeleccionados();
        mAllJugadores = mDaoJugadorSeleccionado.getAllJugador();
    }

    public LiveData<List<JugadorSeleccionado>> getAllJugadores() {
        return mAllJugadores;
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void insert(JugadorSeleccionado jugador) {
        new RepositorioJugadoresSeleccionados.insertAsyncTask(mDaoJugadorSeleccionado).execute(jugador);

    }

    public void deleteJugador(JugadorSeleccionado jugador) {
        new RepositorioJugadoresSeleccionados.deleteJugadorAsyncTask(mDaoJugadorSeleccionado,jugador).execute(jugador);

    }

    public void deleteAllJugadores() {
        new RepositorioJugadoresSeleccionados.deleteAllWordsAsyncTask(mDaoJugadorSeleccionado);
    }

    private static class insertAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {
        private DaoJugadoresSeleccionados mAsyncTaskDao;
        insertAsyncTask(DaoJugadoresSeleccionados dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {
            mAsyncTaskDao.insertJugador(params[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {
        private DaoJugadoresSeleccionados mAsyncTaskDao;
        deleteAllAsyncTask(DaoJugadoresSeleccionados dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {
            mAsyncTaskDao.deleteAllUsers();
            return null;
        }
    }

    private static class deleteJugadorAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {
        private DaoJugadoresSeleccionados mAsyncTaskDao;
        private JugadorSeleccionado jugador;

        deleteJugadorAsyncTask(DaoJugadoresSeleccionados dao, JugadorSeleccionado jugadorSeleccionado) {
            mAsyncTaskDao = dao;
            jugador= jugadorSeleccionado;
        }
        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {
            mAsyncTaskDao.deleteJugadorSeleccionado(jugador.getNombre());
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DaoJugadoresSeleccionados mAsyncTaskDao;

        deleteAllWordsAsyncTask(DaoJugadoresSeleccionados dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllUsers();
            return null;
        }
    }


}