package com.abel.miequipo.data.allJugadores;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "jugadores")
public class JugadorEntitie {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "nombre")
    String nombre;

    @ColumnInfo
    String imagen = "";


    public JugadorEntitie(String nombre, String imagen) {
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
