package com.tracker.dao;

import com.tracker.modelo.Plataforma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//dao para operaciones sobre la tabla plataforma y la tabla de relación juego_plataforma.
public class PlataformaDAO {

    //insert a la bd de la plataforma, retorna el objeto con su id
    public Plataforma insertar(Plataforma plataforma) throws SQLException {
        String sql = "INSERT INTO plataforma (nombre) VALUES (?) ON CONFLICT (nombre) DO NOTHING RETURNING id_plataforma";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plataforma.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    plataforma.setIdPlataforma(rs.getInt("id_plataforma"));
                } else {
                    Plataforma existente = buscarPorNombre(plataforma.getNombre());
                    if (existente != null) {
                        plataforma.setIdPlataforma(existente.getIdPlataforma());
                    }
                }
            }
        }
        return plataforma;
    }

    //busca plataforma por su nombre
    public Plataforma buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_plataforma, nombre FROM plataforma WHERE nombre = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Plataforma(rs.getInt("id_plataforma"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    //luistar todas las plataformas
    public List<Plataforma> listarTodas() throws SQLException {
        List<Plataforma> plataformas = new ArrayList<>();
        String sql = "SELECT id_plataforma, nombre FROM plataforma ORDER BY nombre";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                plataformas.add(new Plataforma(rs.getInt("id_plataforma"), rs.getString("nombre")));
            }
        }
        return plataformas;
    }

    //listar plataformas que se asocian a algun juego
    public List<Plataforma> listarPorJuego(int idJuego) throws SQLException {
        List<Plataforma> plataformas = new ArrayList<>();
        String sql = "SELECT p.id_plataforma, p.nombre FROM plataforma p " +
                     "JOIN juego_plataforma jp ON p.id_plataforma = jp.fk_plataforma " +
                     "WHERE jp.fk_juego = ? ORDER BY p.nombre";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    plataformas.add(new Plataforma(rs.getInt("id_plataforma"), rs.getString("nombre")));
                }
            }
        }
        return plataformas;
    }


    //agrega una plataforma a un juego (relación juego_plataforma).
    public void agregarAJuego(int idJuego, int idPlataforma) throws SQLException {
        String sql = "INSERT INTO juego_plataforma (fk_juego, fk_plataforma) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            ps.setInt(2, idPlataforma);
            ps.executeUpdate();
        }
    }
}
