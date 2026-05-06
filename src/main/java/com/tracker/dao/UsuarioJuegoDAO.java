package com.tracker.dao;

import com.tracker.modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//dao de la tabla principal de publicaciones
public class UsuarioJuegoDAO {

    private final JuegoDAO juegoDAO = new JuegoDAO();
    private final PlataformaDAO plataformaDAO = new PlataformaDAO();


     //agrega un juego a la biblioteca del usuario
    public UsuarioJuego agregar(UsuarioJuego uj) throws SQLException {
        String sql = "INSERT INTO usuario_juego (fk_usuario, fk_juego, fk_plataforma, estado, calificacion, resena) " +
                     "VALUES (?, ?, ?, ?::estado_juego, ?, ?) RETURNING id_usuario_juego, fecha_agregado";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uj.getFkUsuario());
            ps.setInt(2, uj.getFkJuego());
            if (uj.getFkPlataforma() != null) {
                ps.setInt(3, uj.getFkPlataforma());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, uj.getEstado().name());
            if (uj.getCalificacion() != null) {
                ps.setInt(5, uj.getCalificacion());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setString(6, uj.getResena());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    uj.setIdUsuarioJuego(rs.getInt("id_usuario_juego"));
                    uj.setFechaAgregado(rs.getTimestamp("fecha_agregado"));
                }
            }
        }
        return uj;
    }

    //actualiza estado, calificación y reseña de un registro.

    public void actualizar(int idUsuarioJuego, EstadoJuego estado, Integer calificacion, String resena) throws SQLException {
        String sql = "UPDATE usuario_juego SET estado = ?::estado_juego, calificacion = ?, resena = ? WHERE id_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            if (calificacion != null) {
                ps.setInt(2, calificacion);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, resena);
            ps.setInt(4, idUsuarioJuego);
            ps.executeUpdate();
        }
    }


    //elimina un registro de la biblioteca del usuario
    public void eliminar(int idUsuarioJuego) throws SQLException {
        String sql = "DELETE FROM usuario_juego WHERE id_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioJuego);
            ps.executeUpdate();
        }
    }


    //lista todos los juegos de un usuario con datos del juego y plataforma
    public List<UsuarioJuego> listarPorUsuario(int idUsuario) throws SQLException {
        List<UsuarioJuego> lista = new ArrayList<>();
        String sql = "SELECT uj.id_usuario_juego, uj.fk_usuario, uj.fk_juego, uj.fk_plataforma, " +
                     "uj.estado, uj.calificacion, uj.resena, uj.fecha_agregado, " +
                     "j.api_id, j.nombre AS juego_nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "p.nombre AS plataforma_nombre " +
                     "FROM usuario_juego uj " +
                     "JOIN juego j ON uj.fk_juego = j.id_juego " +
                     "LEFT JOIN plataforma p ON uj.fk_plataforma = p.id_plataforma " +
                     "WHERE uj.fk_usuario = ? ORDER BY uj.fecha_agregado DESC";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearConJuego(rs));
                }
            }
        }
        return lista;
    }

    //busca un registro por su id
    public UsuarioJuego buscar(int idUsuarioJuego) throws SQLException {
        String sql = "SELECT uj.id_usuario_juego, uj.fk_usuario, uj.fk_juego, uj.fk_plataforma, " +
                     "uj.estado, uj.calificacion, uj.resena, uj.fecha_agregado, " +
                     "j.api_id, j.nombre AS juego_nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "p.nombre AS plataforma_nombre " +
                     "FROM usuario_juego uj " +
                     "JOIN juego j ON uj.fk_juego = j.id_juego " +
                     "LEFT JOIN plataforma p ON uj.fk_plataforma = p.id_plataforma " +
                     "WHERE uj.id_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioJuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearConJuego(rs);
                }
            }
        }
        return null;
    }


    //filtra los juegos de un usuario por estado
    public List<UsuarioJuego> filtrarPorEstado(int idUsuario, EstadoJuego estado) throws SQLException {
        List<UsuarioJuego> lista = new ArrayList<>();
        String sql = "SELECT uj.id_usuario_juego, uj.fk_usuario, uj.fk_juego, uj.fk_plataforma, " +
                     "uj.estado, uj.calificacion, uj.resena, uj.fecha_agregado, " +
                     "j.api_id, j.nombre AS juego_nombre, j.img_url, j.metacritic, j.fecha_lanzamiento, " +
                     "p.nombre AS plataforma_nombre " +
                     "FROM usuario_juego uj " +
                     "JOIN juego j ON uj.fk_juego = j.id_juego " +
                     "LEFT JOIN plataforma p ON uj.fk_plataforma = p.id_plataforma " +
                     "WHERE uj.fk_usuario = ? AND uj.estado = ?::estado_juego " +
                     "ORDER BY uj.fecha_agregado DESC";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, estado.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearConJuego(rs));
                }
            }
        }
        return lista;
    }


    //obtiene estadísticas del usuario se hace conteo por cada estado
    public Map<String, Integer> obtenerEstadisticas(int idUsuario) throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT estado, COUNT(*) AS total FROM usuario_juego WHERE fk_usuario = ? GROUP BY estado";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stats.put(rs.getString("estado"), rs.getInt("total"));
                }
            }
        }
        // Agregar total general
        int total = 0;
        for (int v : stats.values()) total += v;
        stats.put("TOTAL", total);
        return stats;
    }


    //cuenta los votos de un registro.
    public int[] contarVotos(int idUsuarioJuego) throws SQLException {
        int[] votos = {0, 0}; // [positivos, negativos]
        String sql = "SELECT positivo, COUNT(*) AS total FROM voto WHERE fk_usuario_juego = ? GROUP BY positivo";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioJuego);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getBoolean("positivo")) {
                        votos[0] = rs.getInt("total");
                    } else {
                        votos[1] = rs.getInt("total");
                    }
                }
            }
        }
        return votos;
    }


    //metodo privado para mapear el resultset a un objeto UsuarioJuego
    private UsuarioJuego mapearConJuego(ResultSet rs) throws SQLException {
        UsuarioJuego uj = new UsuarioJuego();
        uj.setIdUsuarioJuego(rs.getInt("id_usuario_juego"));
        uj.setFkUsuario(rs.getInt("fk_usuario"));
        uj.setFkJuego(rs.getInt("fk_juego"));
        int plat = rs.getInt("fk_plataforma");
        uj.setFkPlataforma(rs.wasNull() ? null : plat);
        uj.setEstado(EstadoJuego.valueOf(rs.getString("estado")));
        int cal = rs.getInt("calificacion");
        uj.setCalificacion(rs.wasNull() ? null : cal);
        uj.setResena(rs.getString("resena"));
        uj.setFechaAgregado(rs.getTimestamp("fecha_agregado"));

        //juego asociado
        Juego juego = new Juego();
        juego.setIdJuego(uj.getFkJuego());
        juego.setApiId(rs.getString("api_id"));
        juego.setNombre(rs.getString("juego_nombre"));
        juego.setImgUrl(rs.getString("img_url"));
        double mc = rs.getDouble("metacritic");
        juego.setMetacritic(rs.wasNull() ? null : mc);
        juego.setFechaLanzamiento(rs.getDate("fecha_lanzamiento"));
        uj.setJuego(juego);

        //plataforma asociada
        String platNombre = rs.getString("plataforma_nombre");
        if (platNombre != null) {
            Plataforma p = new Plataforma();
            p.setIdPlataforma(uj.getFkPlataforma() != null ? uj.getFkPlataforma() : 0);
            p.setNombre(platNombre);
            uj.setPlataforma(p);
        }

        return uj;
    }
}
