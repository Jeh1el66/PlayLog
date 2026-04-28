package com.tracker.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



//javabean para los generos de los juegos, aqui ya se toman en cuenta sus generos y plataformas


public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idJuego;
    private String apiId;
    private String nombre;
    private String imgUrl;
    private Double metacritic;
    private Date fechaLanzamiento;
    private List<Genero> generos;
    private List<Plataforma> plataformas;

    public Juego() {
        this.generos = new ArrayList<>();
        this.plataformas = new ArrayList<>();
    }

    public Juego(int idJuego, String apiId, String nombre, String imgUrl,
                 Double metacritic, Date fechaLanzamiento) {
        this.idJuego = idJuego;
        this.apiId = apiId;
        this.nombre = nombre;
        this.imgUrl = imgUrl;
        this.metacritic = metacritic;
        this.fechaLanzamiento = fechaLanzamiento;
        this.generos = new ArrayList<>();
        this.plataformas = new ArrayList<>();
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(Double metacritic) {
        this.metacritic = metacritic;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }
}
