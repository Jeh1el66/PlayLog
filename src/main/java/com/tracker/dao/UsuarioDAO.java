package com.tracker.dao;

import com.tracker.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


 //dao para operaciones CRUD sobre la tabla usuario

public class UsuarioDAO {


    //inserta un nuevo usuario y retorna el objeto con su id generado

    public Usuario insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, password_hash) VALUES (?, ?, ?) RETURNING id_usuario, fecha_registro";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPasswordHash());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                }
            }
        }
        return usuario;
    }

    //busca user por su email
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, password_hash, fecha_registro FROM usuario WHERE email = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    //busca user por su id
    public Usuario buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, password_hash, fecha_registro FROM usuario WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    //actualizar nombre y email de algun usuario
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, email = ? WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setInt(3, usuario.getIdUsuario());
            ps.executeUpdate();
        }
    }
    //metodo aux para convertir resultado de bd en objeto usuario
    //y usarlos en otros metodos de la clase
    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return u;
    }
}
