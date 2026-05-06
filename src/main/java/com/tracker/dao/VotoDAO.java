package com.tracker.dao;

import com.tracker.modelo.Voto;

import java.sql.*;

//dao para la tabla de Voto
public class VotoDAO {


     //registra un voto, si ya existe no se hace nada
    public void votar(int idUsuario, int idUsuarioJuego, boolean positivo) throws SQLException {
        String sql = "INSERT INTO voto (fk_usuario, fk_usuario_juego, positivo) VALUES (?, ?, ?) " +
                     "ON CONFLICT (fk_usuario, fk_usuario_juego) DO UPDATE SET positivo = ?, fecha = NOW()";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuarioJuego);
            ps.setBoolean(3, positivo);
            ps.setBoolean(4, positivo);
            ps.executeUpdate();
        }
    }

   //quita un voto existente
    public void quitarVoto(int idUsuario, int idUsuarioJuego) throws SQLException {
        String sql = "DELETE FROM voto WHERE fk_usuario = ? AND fk_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuarioJuego);
            ps.executeUpdate();
        }
    }

    //cambia el voto que ya existe
    public void cambiarVoto(int idUsuario, int idUsuarioJuego, boolean positivo) throws SQLException {
        String sql = "UPDATE voto SET positivo = ?, fecha = NOW() WHERE fk_usuario = ? AND fk_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, positivo);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idUsuarioJuego);
            ps.executeUpdate();
        }
    }

    //cuenta los votos positivos de un registro
    public int contarPositivos(int idUsuarioJuego) throws SQLException {
        String sql = "SELECT COUNT(*) FROM voto WHERE fk_usuario_juego = ? AND positivo = true";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioJuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    //cuenta los votos negativos de un registro
    public int contarNegativos(int idUsuarioJuego) throws SQLException {
        String sql = "SELECT COUNT(*) FROM voto WHERE fk_usuario_juego = ? AND positivo = false";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioJuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    //busca el voto de un usuario en un registro específico.
    public Voto buscarVoto(int idUsuario, int idUsuarioJuego) throws SQLException {
        String sql = "SELECT id_voto, fk_usuario, fk_usuario_juego, positivo, fecha FROM voto " +
                     "WHERE fk_usuario = ? AND fk_usuario_juego = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuarioJuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Voto v = new Voto();
                    v.setIdVoto(rs.getInt("id_voto"));
                    v.setFkUsuario(rs.getInt("fk_usuario"));
                    v.setFkUsuarioJuego(rs.getInt("fk_usuario_juego"));
                    v.setPositivo(rs.getBoolean("positivo"));
                    v.setFecha(rs.getTimestamp("fecha"));
                    return v;
                }
            }
        }
        return null;
    }
}
