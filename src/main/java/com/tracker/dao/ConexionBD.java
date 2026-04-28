package com.tracker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 //conexion a la base de datos PostgreSQL.
 //Las credenciales se leen desde variables de entorno en este caso las pondre directo al tomcat


public class ConexionBD {

    private static ConexionBD instancia;

    private final String url;
    private final String usuario;
    private final String password;

    private ConexionBD() {
        String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "5432";
        String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "playlogprueba";
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        this.usuario = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        this.password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "an128976";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de PostgreSQL", e);
        }
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }
}
