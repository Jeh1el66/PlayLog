package com.tracker.servicio;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


//servicio de autenticacion
//validacionn de JWT y hasheo de contraseñas con BCrypt.

public class AuthService {

    private static final long EXPIRACION_MS = 24 * 60 * 60 * 1000;//24 hrs
    private final SecretKey claveSecreta;

    public AuthService() {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < 32) {
            //clave por defecto de al menos 256 bits para HMAC-SHA256
            secret = "PlayLogSuperSecretKeyQueDebeSerAlMenos32Chars!!";
        }
        this.claveSecreta = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    //genera un token JWT con el id del usuario como subject
    public String generarToken(int idUsuario, String nombre) {
        return Jwts.builder()
                .setSubject(String.valueOf(idUsuario))
                .claim("nombre", nombre)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION_MS))
                .signWith(claveSecreta, SignatureAlgorithm.HS256)
                .compact();
    }


    //valida un token JWT y retorna los claims si es valido si no retorna null
    public Claims validarToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(claveSecreta)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    //hashear con bcrypt
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    //verificar contra e bcrypt
    public boolean verificarPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
