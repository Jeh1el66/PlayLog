package com.tracker.dao;

import com.tracker.modelo.Juego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


 //dao para CRUD sobre la tabla juego.

public class JuegoDAO {


    //isnerta un nuevo juego y retorna el objeto con su id generado

    public Juego insertar(Juego juego) throws SQLException {
        String sql = "INSERT INTO juego (api_id, nombre, img_url, metacritic, fecha_lanzamiento) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id_juego";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, juego.getApiId());
            ps.setString(2, juego.getNombre());
            ps.setString(3, juego.getImgUrl());
            if (juego.getMetacritic() != null) {
                ps.setDouble(4, juego.getMetacritic());
            } else {
                ps.setNull(4, Types.NUMERIC);
            }
            if (juego.getFechaLanzamiento() != null) {
                ps.setDate(5, juego.getFechaLanzamiento());
            } else {
                ps.setNull(5, Types.DATE);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    juego.setIdJuego(rs.getInt("id_juego"));
                }
            }
        }
        return juego;
    }


     //busca un juego por su api id externo
    public Juego buscarPorApiId(String apiId) throws SQLException {
        String sql = "SELECT id_juego, api_id, nombre, img_url, metacritic, fecha_lanzamiento FROM juego WHERE api_id = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, apiId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }


    //guarda un juego si no existe ya en la BD. Retorna el juego existente o el recien insertado
    //se hace asi ya que si ya se consulto de la api no es necesario volver a consultarla
    public Juego guardarSiNoExiste(Juego juego) throws SQLException {
        Juego existente = buscarPorApiId(juego.getApiId());
        if (existente != null) {
            return existente;
        }
        return insertar(juego);
    }

    //lista todos los juegos
    public List<Juego> listarTodos() throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT id_juego, api_id, nombre, img_url, metacritic, fecha_lanzamiento FROM juego ORDER BY nombre";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                juegos.add(mapear(rs));
            }
        }
        return juegos;
    }

    //busqueda de juegos por nombre, puede ser coincidencias cercanas a juegos
    //por ej: Grand Theft, pone todos los gta
    public List<Juego> buscarPorNombre(String nombre) throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT id_juego, api_id, nombre, img_url, metacritic, fecha_lanzamiento FROM juego WHERE LOWER(nombre) LIKE LOWER(?) ORDER BY nombre";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    juegos.add(mapear(rs));
                }
            }
        }
        return juegos;
    }



    //lista los juegos mas deseados, con estado QUIERO_JUGAR, ordenados por cantidad
    public List<Juego> listarMasDeseados(int limite) throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT j.id_juego, j.api_id, j.nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "COUNT(uj.id_usuario_juego) AS total " +
                     "FROM juego j JOIN usuario_juego uj ON j.id_juego = uj.fk_juego " +
                     "WHERE uj.estado = 'QUIERO_JUGAR' " +
                     "GROUP BY j.id_juego ORDER BY total DESC LIMIT ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    juegos.add(mapear(rs));
                }
            }
        }
        return juegos;
    }


     //lista los juegos mejor votados, segun la cantidad de votos positivos
    public List<Juego> listarMejorVotados(int limite) throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT j.id_juego, j.api_id, j.nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "COUNT(CASE WHEN v.positivo = true THEN 1 END) AS positivos " +
                     "FROM juego j " +
                     "JOIN usuario_juego uj ON j.id_juego = uj.fk_juego " +
                     "JOIN voto v ON uj.id_usuario_juego = v.fk_usuario_juego " +
                     "GROUP BY j.id_juego ORDER BY positivos DESC LIMIT ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    juegos.add(mapear(rs));
                }
            }
        }
        return juegos;
    }

    //lista los juegos mas completados
    public List<Juego> listarMasCompletados(int limite) throws SQLException {
        List<Juego> juegos = new ArrayList<>();
        String sql = "SELECT j.id_juego, j.api_id, j.nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "COUNT(uj.id_usuario_juego) AS total " +
                     "FROM juego j JOIN usuario_juego uj ON j.id_juego = uj.fk_juego " +
                     "WHERE uj.estado = 'COMPLETADO' " +
                     "GROUP BY j.id_juego ORDER BY total DESC LIMIT ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    juegos.add(mapear(rs));
                }
            }
        }
        return juegos;
    }

   //busca juego por el id de la bd
    public Juego buscarPorId(int idJuego) throws SQLException {
        String sql = "SELECT id_juego, api_id, nombre, img_url, metacritic, fecha_lanzamiento FROM juego WHERE id_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    //metodo aux para convertir resultado de bd en objeto juego
    //y usarlos en otros metodos de la clase
    private Juego mapear(ResultSet rs) throws SQLException {
        Juego j = new Juego();
        j.setIdJuego(rs.getInt("id_juego"));
        j.setApiId(rs.getString("api_id"));
        j.setNombre(rs.getString("nombre"));
        j.setImgUrl(rs.getString("img_url"));
        double mc = rs.getDouble("metacritic");
        j.setMetacritic(rs.wasNull() ? null : mc);
        j.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
        return j;
    }
}
