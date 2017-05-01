package com.juandaqugo.hospitalnearby;

/**
 * Created by Camilo on 01/05/2017.
 */

public class Contactos {
    String documento, nombre;
    public Contactos(){

    }

    public Contactos(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;

    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }
}
