package com.abel.miequipo.data.rankinJugadores;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "table_rankin")
public class JugadorRankin {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "nombre")
    String nombre;

    @ColumnInfo(name = "imagen")
    String imagen ;

    @ColumnInfo(name = "goles")
    String goles ;

    @ColumnInfo(name = "partidosGanados")
    int partidosGanados;

    @ColumnInfo(name = "campeonatosGanados")
    int campeonatosGanados;


    public JugadorRankin(@NonNull String nombre, String imagen, String goles, int partidosGanados, int campeonatosGanados) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.goles = goles;
        this.partidosGanados = partidosGanados;
        this.campeonatosGanados = campeonatosGanados;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getGoles() {
        return goles;
    }

    public void setGoles(String goles) {
        this.goles = goles;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getCampeonatosGanados() {
        return campeonatosGanados;
    }

    public void setCampeonatosGanados(int campeonatosGanados) {
        this.campeonatosGanados = campeonatosGanados;
    }
}
