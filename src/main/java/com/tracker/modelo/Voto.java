package com.tracker.modelo;

import java.io.Serializable;
import java.sql.Timestamp;



//javabean que representa un voto booleano para sobre un registro en usuario_juego

public class Voto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idVoto;
    private int fkUsuario;
    private int fkUsuarioJuego;
    private boolean positivo;
    private Timestamp fecha;

    public Voto() {
    }

    public int getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(int idVoto) {
        this.idVoto = idVoto;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public int getFkUsuarioJuego() {
        return fkUsuarioJuego;
    }

    public void setFkUsuarioJuego(int fkUsuarioJuego) {
        this.fkUsuarioJuego = fkUsuarioJuego;
    }

    public boolean isPositivo() {
        return positivo;
    }

    public void setPositivo(boolean positivo) {
        this.positivo = positivo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
