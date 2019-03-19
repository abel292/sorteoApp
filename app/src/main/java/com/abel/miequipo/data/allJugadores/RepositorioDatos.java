package com.abel.miequipo.data.allJugadores;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.abel.miequipo.data.MyDataBaseJugadores;

import java.util.List;

/**
 * Using the Room database as a data source.
 */
public class RepositorioDatos {


    private DaoJugador mDaoJugador;
    private LiveData<List<JugadorEntitie>> mAllJugadores;

    public RepositorioDatos(Application application) {
        MyDataBaseJugadores db = MyDataBaseJugadores.getDatabase(application);
        mDaoJugador = db.daoJugador();
        mAllJugadores = mDaoJugador.getAllJugador();
    }

    public LiveData<List<JugadorEntitie>> getAllJugadores() {
        return mAllJugadores;
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void insert(JugadorEntitie jugador) {
        new insertAsyncTask(mDaoJugador).execute(jugador);
    }

    public void deleteAllJugadores() {
        new deleteAllAsyncTask(mDaoJugador);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class insertAsyncTask extends AsyncTask<JugadorEntitie, Void, Void> {

        private DaoJugador mAsyncTaskDao;

        insertAsyncTask(DaoJugador dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JugadorEntitie... params) {
            mAsyncTaskDao.insertJugador(params[0]);
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
