package com.onlyphones.onlyphones.security;

import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor

public class JwtUtils {

    Dotenv dotenv = Dotenv.load();
    final String key = dotenv.get("JWT");
    private static final long EXPIRATION_TIME = 1000L*60*60;
    private final Key secretKey = Keys.hmacShaKeyFor(key.getBytes());
    private final UserRepository userRepository;
    Logger logger = Logger.getLogger(getClass().getName());

    public String generateToken(User user) {

        User userByEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("El correo no se encuentra registrado"));
        String rol = userByEmail.getUserRol().getRol();
        logger.info(key);


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
