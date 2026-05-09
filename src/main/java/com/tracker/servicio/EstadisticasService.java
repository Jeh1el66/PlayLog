package com.tracker.servicio;

import com.tracker.dao.JuegoDAO;
import com.tracker.dao.UsuarioJuegoDAO;
import com.tracker.modelo.Juego;
import com.tracker.modelo.UsuarioJuego;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//servicio de estadisticas globales y por usuario que se usaran en la pagina
public class EstadisticasService {

    private final JuegoDAO juegoDAO = new JuegoDAO();
    private final UsuarioJuegoDAO usuarioJuegoDAO = new UsuarioJuegoDAO();


    //obtiene los juegos mas deseados (QUIERO_JUGAR) globalmente.
    public List<Juego> juegosMasDeseados(int limite) throws SQLException {
        return juegoDAO.listarMasDeseados(limite);
    }


    //Obtiene los juegos mejor votados globalmente
    public List<Juego> mejoresVotados(int limite) throws SQLException {
        return juegoDAO.listarMejorVotados(limite);
    }


    //obtiene los juegos mas completados globalmente
    public List<Juego> masCompletados(int limite) throws SQLException {
        return juegoDAO.listarMasCompletados(limite);
    }


    //obtiene las estadisticas de un usuario: conteo por estado.
    public Map<String, Integer> statsDeUsuario(int idUsuario) throws SQLException {
        return usuarioJuegoDAO.obtenerEstadisticas(idUsuario);
    }
}
