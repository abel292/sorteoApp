package com.abel.miequipo.data.allJugadores;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.abel.miequipo.data.MyDataBaseJugadores;
import com.abel.miequipo.data.seleccionJugadores.DaoJugadoresSeleccionados;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados;

import java.util.List;

/**
 * Using the Room database as a data source.
 */
public class RepositorioDatos {


    private DaoJugadoresSeleccionados mDaoJugadorSeleccionado;
    private DaoJugador mDaoJugador;
    private LiveData<List<JugadorEntitie>> mAllJugadores;
    private RepositorioJugadoresSeleccionados repositorioJugadoresSeleccionados;

    public RepositorioDatos(Application application) {
        MyDataBaseJugadores db = MyDataBaseJugadores.getDatabase(application);
        mDaoJugador = db.daoJugador();
        mDaoJugadorSeleccionado= db.daoJugadoresSeleccionados();
        mAllJugadores = mDaoJugador.getAllJugador();
        repositorioJugadoresSeleccionados= new RepositorioJugadoresSeleccionados(application);
    }

    public LiveData<List<JugadorEntitie>> getAllJugadores() {
        return mAllJugadores;
    }

    public void deleteJugador(JugadorEntitie jugador) {
        new RepositorioDatos.deleteJugadorAsyncTask(mDaoJugador,jugador).execute(jugador);

    }

    public void moveAllToPlayerList( LiveData<List<JugadorSeleccionado>> mJugadoresSelected ) {

        for (JugadorSeleccionado jugador: mJugadoresSelected.getValue()){
            JugadorEntitie jugadorEntitie= new JugadorEntitie(jugador.getNombre(),jugador.getImagen());
            insert(jugadorEntitie);
            Log.e("TAGER",jugador.getNombre());
            repositorioJugadoresSeleccionados.deleteJugador(jugador);
        }
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

    private static class deleteJugadorAsyncTask extends AsyncTask<JugadorEntitie, Void, Void> {
        private DaoJugador mAsyncTaskDao;
        private JugadorEntitie jugador;

        deleteJugadorAsyncTask(DaoJugador dao, JugadorEntitie jugador) {
            mAsyncTaskDao = dao;
            this.jugador= jugador;
        }
        @Override
        protected Void doInBackground(final JugadorEntitie... params) {
            mAsyncTaskDao.deleteJugadorSeleccionado(jugador.getNombre());
            return null;
        }
    }
}
