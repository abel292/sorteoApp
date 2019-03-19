package com.abel.miequipo.data.seleccionJugadores;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "jugadores_seleccionados")
public class JugadorSeleccionado {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "nombre")
    String nombre;

    String imagen = "";


    public JugadorSeleccionado(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
