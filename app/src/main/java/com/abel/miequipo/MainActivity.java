package com.abel.miequipo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_equipos:
                    return true;
                case R.id.navigation_favoritos:
                    return true;
                case R.id.navigation_jugadores:
                    return true;
            }
            return false;
        }
    };

    private Fragment mCurrentFragment;
    private FragmentEquipos fragmentEquipos;
    private FragmentFavoritos fragmentFavoritos;
    private FragmentJugadores fragmentJugadores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final FragmentManager mFragmentManager = getSupportFragmentManager();


        fragmentEquipos = new FragmentEquipos();
        fragmentFavoritos = new FragmentFavoritos();
        fragmentJugadores = new FragmentJugadores();


        /*navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_equipos:
                        mCurrentFragment = fragmentEquipos;
                        break;
                    case R.id.navigation_favoritos:
                        mCurrentFragment = fragmentFavoritos;
                        break;
                    case R.id.navigation_jugadores:
                        mCurrentFragment = fragmentJugadores;
                        break;

                }
                //getFragmentManager().executePendingTransactions();
                final FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
                //transaction1.replace(R.id.mainContainer, mCurrentFragment);
                transaction1.replace(R.id.mainContainer, mCurrentFragment);
                transaction1.commitAllowingStateLoss();
                return true;
            }});*/

        final FragmentTransaction transaction1 = mFragmentManager.beginTransaction();
        //transaction1.replace(R.id.mainContainer, mCurrentFragment);
        transaction1.replace(R.id.mainContainer, fragmentJugadores);
        transaction1.commitAllowingStateLoss();
    }

}
