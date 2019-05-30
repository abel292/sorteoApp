package com.abel.miequipo.data.rankinJugadores;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.abel.miequipo.data.MyDataBaseJugadores;
import com.abel.miequipo.data.allJugadores.DaoJugador;
import com.abel.miequipo.data.allJugadores.JugadorEntitie;
import com.abel.miequipo.data.seleccionJugadores.DaoJugadoresSeleccionados;
import com.abel.miequipo.data.seleccionJugadores.JugadorSeleccionado;
import com.abel.miequipo.data.seleccionJugadores.RepositorioJugadoresSeleccionados;

import java.util.List;

/**
 * Using the Room database as a data source.
 */
public class RepositorioRankin {


    private DaoJugadorRankin mDaoJugador;
    private LiveData<List<JugadorRankin>> mAllJugadoresTopCampeonatos;
    private LiveData<List<JugadorRankin>> mAllJugadoresTopPartidos;

    private LiveData<List<JugadorRankin>> mAllJugadoresLocal;

    private LiveData<List<JugadorRankin>> jugador;


    public RepositorioRankin(Application application) {
        MyDataBaseJugadores db = MyDataBaseJugadores.getDatabase(application);
        mDaoJugador = db.daoJugadorRankin();
        mAllJugadoresTopCampeonatos = mDaoJugador.getAllJugadoresTopCampeonatos();
        mAllJugadoresTopPartidos = mDaoJugador.getAllJugadoresTopPartidos();
    }

    public void getAllJugadoresLocal(LiveData<List<JugadorRankin>> mAllJugadoresLocal) {
        this.mAllJugadoresLocal = mAllJugadoresLocal;

    }

    public LiveData<List<JugadorRankin>> getAllJugadores() {
        return mAllJugadoresTopCampeonatos;
    }

    public LiveData<List<JugadorRankin>> getAllJugadoresTopPartidos() {
        return mAllJugadoresTopPartidos;
    }


    public void deleteJugador(JugadorRankin jugador) {
        new deleteJugadorAsyncTask(mDaoJugador, jugador).execute(jugador);

    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void insert(JugadorRankin jugador) {
        new insertAsyncTask(mDaoJugador).execute(jugador);
    }


    public void addPartido(String partidosGanados, JugadorSeleccionado jugador,List<JugadorRankin> mAllJugadoresLocal) {
        new addPartidoAsyncTask(mDaoJugador, partidosGanados, mAllJugadoresLocal).execute(jugador);
    }

    public void addCampeonato(String campeonatoGanado, JugadorSeleccionado jugador,List<JugadorRankin> mAllJugadoresLocal) {
        new addCampeonatoAsyncTask(mDaoJugador, campeonatoGanado, mAllJugadoresLocal).execute(jugador);
    }


    public LiveData<List<JugadorRankin>> getJugador(String nombreJugador) {

        jugador = mDaoJugador.getJugador(nombreJugador);


        return jugador;
    }


    public void deleteAllJugadores() {
        new deleteAllAsyncTask(mDaoJugador);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class insertAsyncTask extends AsyncTask<JugadorRankin, Void, Void> {

        private DaoJugadorRankin mAsyncTaskDao;

        insertAsyncTask(DaoJugadorRankin dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JugadorRankin... params) {
            mAsyncTaskDao.insertJugador(params[0]);
            return null;
        }
    }


    //partido ganados
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class addPartidoAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {

        private DaoJugadorRankin mAsyncTaskDao;
        private int partidosWins;
        private List<JugadorRankin> allJugadores;

        addPartidoAsyncTask(DaoJugadorRankin dao, String partidosGanados,List<JugadorRankin> mAllJugadoresLocal) {
            mAsyncTaskDao = dao;
            partidosWins = Integer.parseInt( partidosGanados);
            allJugadores = mAllJugadoresLocal;
        }

        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {

            for (JugadorRankin jugador : allJugadores) {

                if (jugador.getNombre().equalsIgnoreCase(params[0].getNombre())) {
                    partidosWins =partidosWins +jugador.getPartidosGanados();
                }
            }
            mAsyncTaskDao.addPartido(partidosWins, params[0].getNombre());
            Log.d("add partido", String.valueOf(partidosWins) + " " + params[0].getNombre());

            return null;
        }
    }



    //add campeonato
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class addCampeonatoAsyncTask extends AsyncTask<JugadorSeleccionado, Void, Void> {

        private DaoJugadorRankin mAsyncTaskDao;
        private int campeonatoGanado;
        private List<JugadorRankin> allJugadores;

        addCampeonatoAsyncTask(DaoJugadorRankin dao, String campeonatoGanado,List<JugadorRankin> mAllJugadoresLocal) {
            mAsyncTaskDao = dao;
            this.campeonatoGanado =  Integer.parseInt(campeonatoGanado);
            allJugadores = mAllJugadoresLocal;
        }

        @Override
        protected Void doInBackground(final JugadorSeleccionado... params) {

            for (JugadorRankin jugador : allJugadores) {

                if (jugador.getNombre().equalsIgnoreCase(params[0].getNombre())) {
                    campeonatoGanado =campeonatoGanado + jugador.getCampeonatosGanados();
                }
            }
            mAsyncTaskDao.addCampeonato(campeonatoGanado, params[0].getNombre());
            Log.d("add partido", String.valueOf(campeonatoGanado) + " " + params[0].getNombre());

            return null;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class deleteAllAsyncTask extends AsyncTask<JugadorRankin, Void, Void> {

        private DaoJugadorRankin mAsyncTaskDao;

        deleteAllAsyncTask(DaoJugadorRankin dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JugadorRankin... params) {
            mAsyncTaskDao.deleteAllUsers();
            return null;
        }
    }

    private static class deleteJugadorAsyncTask extends AsyncTask<JugadorRankin, Void, Void> {
        private DaoJugadorRankin mAsyncTaskDao;
        private JugadorRankin jugador;

        deleteJugadorAsyncTask(DaoJugadorRankin dao, JugadorRankin jugador) {
            mAsyncTaskDao = dao;
            this.jugador = jugador;
        }

        @Override
        protected Void doInBackground(final JugadorRankin... params) {
            mAsyncTaskDao.deleteJugadorSeleccionado(jugador.getNombre());
            return null;
        }
    }


   /* @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private static class getJugadorAsyncTask extends AsyncTask<JugadorRankin, Void, Void> {

        private DaoJugadorRankin mAsyncTaskDao;
        String nombreJugador;

        getJugadorAsyncTask(DaoJugadorRankin dao, String nombreJugador) {
            mAsyncTaskDao = dao;
            this.nombreJugador = nombreJugador;
        }

        @Override
        protected Void doInBackground(final JugadorRankin... params) {
            LiveData<JugadorRankin> jugadorRankin= mAsyncTaskDao.getJugador(nombreJugador);

            return null;
        }
    }*/
}
