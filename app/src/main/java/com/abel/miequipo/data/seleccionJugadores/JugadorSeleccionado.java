package com.abel.miequipo.data.seleccionJugadores;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "jugadores_seleccionados")
public class JugadorSeleccionado implements Parcelable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "nombre")
    String nombre;

    String imagen = "";

    //GENERATE
    //para poder imprimir el objeto completo en forma de string
    @Override
    public String toString() {
        return "JugadorSeleccionado{" +
                "nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }

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



    //GENERATE
    //para Parceable y pasarlo por intents u otras cosas mas
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.imagen);
    }

    protected JugadorSeleccionado(Parcel in) {
        this.nombre = in.readString();
        this.imagen = in.readString();
    }

    public static final Parcelable.Creator<JugadorSeleccionado> CREATOR = new Parcelable.Creator<JugadorSeleccionado>() {
        @Override
        public JugadorSeleccionado createFromParcel(Parcel source) {
            return new JugadorSeleccionado(source);
        }

        @Override
        public JugadorSeleccionado[] newArray(int size) {
            return new JugadorSeleccionado[size];
        }
    };
}
