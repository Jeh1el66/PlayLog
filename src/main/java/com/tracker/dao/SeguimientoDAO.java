package com.tracker.dao;

import com.tracker.modelo.Seguimiento;
import com.tracker.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//dao para el seguimiento de usuarios

public class SeguimientoDAO {

    //usuario sigue a otro
    public void seguir(int idSeguidor, int idSeguido) throws SQLException {
        String sql = "INSERT INTO seguimiento (fk_seguidor, fk_seguido) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idSeguido);
            ps.executeUpdate();
        }
    }

    //usuario deja de seguir a otro
    public void dejarDeSeguir(int idSeguidor, int idSeguido) throws SQLException {
        String sql = "DELETE FROM seguimiento WHERE fk_seguidor = ? AND fk_seguido = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idSeguido);
            ps.executeUpdate();
        }
    }

    //ibtener usuarios que te siguen
    public List<Usuario> obtenerSeguidores(int idUsuario) throws SQLException {
        List<Usuario> seguidores = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.fecha_registro " +
                     "FROM usuario u JOIN seguimiento s ON u.id_usuario = s.fk_seguidor " +
                     "WHERE s.fk_seguido = ? ORDER BY s.fecha DESC";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    seguidores.add(u);
                }
            }
        }
        return seguidores;
    }

    //obtener a quien sigues
    public List<Usuario> obtenerSeguidos(int idUsuario) throws SQLException {
        List<Usuario> seguidos = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.fecha_registro " +
                     "FROM usuario u JOIN seguimiento s ON u.id_usuario = s.fk_seguido " +
                     "WHERE s.fk_seguidor = ? ORDER BY s.fecha DESC";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    seguidos.add(u);
                }
            }
        }
        return seguidos;
    }

    //verificar si existe seguimiento entre usuarios
    public boolean existeSeguimiento(int idSeguidor, int idSeguido) throws SQLException {
        String sql = "SELECT 1 FROM seguimiento WHERE fk_seguidor = ? AND fk_seguido = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idSeguido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
