package com.tracker.modelo;

import java.io.Serializable;

//javabean para los generos de los videojuegos
//utilizare serealizable para cumplir con que sea javabean pero como pienso usar JWT,
//no lo veia tan necesario, pero a lo que entendi para que sea javabean ocupas que sea seralizable
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idGenero;
    private String nombre;

    public Genero() {
    }

    public Genero(int idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
