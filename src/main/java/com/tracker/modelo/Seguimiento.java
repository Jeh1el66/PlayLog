package com.tracker.modelo;

import java.io.Serializable;
import java.sql.Timestamp;


//javabean para el segumiento de los usuarios
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idSeguimiento;
    private int fkSeguidor;
    private int fkSeguido;
    private Timestamp fecha;

    //variables para mostrar en vistas
    private Usuario seguidor;
    private Usuario seguido;

    public Seguimiento() {
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public int getFkSeguidor() {
        return fkSeguidor;
    }

    public void setFkSeguidor(int fkSeguidor) {
        this.fkSeguidor = fkSeguidor;
    }

    public int getFkSeguido() {
        return fkSeguido;
    }

    public void setFkSeguido(int fkSeguido) {
        this.fkSeguido = fkSeguido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Usuario getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
    }

    public Usuario getSeguido() {
        return seguido;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
    }
}
