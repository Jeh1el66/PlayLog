package com.tracker.modelo;

import java.io.Serializable;
import java.sql.Timestamp;


//javabean ppara la relacion entre el user y un  juego en su biblioteca

public class UsuarioJuego implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuarioJuego;
    private int fkUsuario;
    private int fkJuego;
    private Integer fkPlataforma;
    private EstadoJuego estado;
    private Integer calificacion;
    private String resena;
    private Timestamp fechaAgregado;

    //obejtos  para mostrar en vistas
    private Usuario usuario;
    private Juego juego;
    private Plataforma plataforma;

    //conteo de votos para mostrar en vistas
    private int votosPositivos;
    private int votosNegativos;

    public UsuarioJuego() {
    }

    public int getIdUsuarioJuego() {
        return idUsuarioJuego;
    }

    public void setIdUsuarioJuego(int idUsuarioJuego) {
        this.idUsuarioJuego = idUsuarioJuego;
    }

    public int getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(int fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public int getFkJuego() {
        return fkJuego;
    }

    public void setFkJuego(int fkJuego) {
        this.fkJuego = fkJuego;
    }

    public Integer getFkPlataforma() {
        return fkPlataforma;
    }

    public void setFkPlataforma(Integer fkPlataforma) {
        this.fkPlataforma = fkPlataforma;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public Timestamp getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Timestamp fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public int getVotosPositivos() {
        return votosPositivos;
    }

    public void setVotosPositivos(int votosPositivos) {
        this.votosPositivos = votosPositivos;
    }

    public int getVotosNegativos() {
        return votosNegativos;
    }

    public void setVotosNegativos(int votosNegativos) {
        this.votosNegativos = votosNegativos;
    }
}
