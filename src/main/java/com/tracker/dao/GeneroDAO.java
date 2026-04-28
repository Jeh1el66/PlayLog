package com.tracker.dao;

import com.tracker.modelo.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//dao para operaciones sobre la tabla genero y la tabla de relacion juego_genero.

public class GeneroDAO {

    //inserta un nuevo gernero
    public Genero insertar(Genero genero) throws SQLException {
        String sql = "INSERT INTO genero (nombre) VALUES (?) ON CONFLICT (nombre) DO NOTHING RETURNING id_genero";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, genero.getNombre());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    genero.setIdGenero(rs.getInt("id_genero"));
                } else {
                    // Ya existía, buscar por nombre
                    Genero existente = buscarPorNombre(genero.getNombre());
                    if (existente != null) {
                        genero.setIdGenero(existente.getIdGenero());
                    }
                }
            }
        }
        return genero;
    }

   //busca genero por su nombre
    public Genero buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id_genero, nombre FROM genero WHERE nombre = ?";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Genero(rs.getInt("id_genero"), rs.getString("nombre"));
                }
            }
        }
        return null;
    }

    //listar generos asociados a un juego
    public List<Genero> listarPorJuego(int idJuego) throws SQLException {
        List<Genero> generos = new ArrayList<>();
        String sql = "SELECT g.id_genero, g.nombre FROM genero g " +
                     "JOIN juego_genero jg ON g.id_genero = jg.fk_genero " +
                     "WHERE jg.fk_juego = ? ORDER BY g.nombre";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    generos.add(new Genero(rs.getInt("id_genero"), rs.getString("nombre")));
                }
            }
        }
        return generos;
    }


    //agrega un genero a un juego en la relacion juego_genero
    public void agregarAJuego(int idJuego, int idGenero) throws SQLException {
        String sql = "INSERT INTO juego_genero (fk_juego, fk_genero) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = ConexionBD.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJuego);
            ps.setInt(2, idGenero);
            ps.executeUpdate();
        }
    }
}
