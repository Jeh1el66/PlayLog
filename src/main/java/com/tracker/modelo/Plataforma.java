package com.tracker.modelo;

import java.io.Serializable;

//javabean para las plaaforma de los videojuegos
public class Plataforma implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPlataforma;
    private String nombre;

    public Plataforma() {
    }

    public Plataforma(int idPlataforma, String nombre) {
        this.idPlataforma = idPlataforma;
        this.nombre = nombre;
    }

    public int getIdPlataforma() {
        return idPlataforma;
    }

    public void setIdPlataforma(int idPlataforma) {
        this.idPlataforma = idPlataforma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
