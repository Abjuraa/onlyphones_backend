package com.onlyphones.onlyphones.security;

import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component

public class JwtUtils {

    private static final long EXPIRATION_TIME = 1000L*60*60;
    private final Key secretKey;
    private final UserRepository userRepository;

    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            UserRepository userRepository) {

        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {

        User userByEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("El correo no se encuentra registrado"));
        String rol = userByEmail.getUserRol().getRol();

        return Jwts.builder()
                //a quien se le va a asignar el token
                .setSubject(user.getIdUser().toString())
                //Tipo de rol o permisos asignados
                .claim("rol", rol)
                .claim("email", userByEmail.getEmail())
                .claim("nombre", user.getName())
                //fecha de creacion
                .setIssuedAt(new Date())
                //fecha de vencimiento
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //firma del token
                .signWith(secretKey)
                //Genera el token
                .compact();

    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                //Pasamos la clave secreta
                .setSigningKey(secretKey)
                //Constuye el parser final para ser analizado
                .build()
                //analiza el token y lo devuelve en una lista
                .parseClaimsJws(token)
                //Obtenemos el Claim del parser
                .getBody()
                //obtenemos los datos guardados en el token
                .getSubject();
    }

    public String getRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol", String.class);
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
