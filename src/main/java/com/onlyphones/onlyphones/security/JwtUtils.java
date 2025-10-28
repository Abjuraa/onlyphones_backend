package com.onlyphones.onlyphones.security;

import com.onlyphones.onlyphones.entity.Rol;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor

public class JwtUtils {

    private static final long EXPIRATION_TIME = 1000*60*60;
    private final Key SECRETKEY = Keys.hmacShaKeyFor("constrasenasupersecretadetodalavida".getBytes());
    private final UserRepository userRepository;

    public String generateToken(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El correo no se encuentra registrado"));
        String rol = user.getUserRol().getRol();

        return Jwts.builder()
                //a quien se le va a asignar el token
                .setSubject(email)
                //Tipo de rol o permisos asignados
                .claim("rol", rol)
                //fecha de creacion
                .setIssuedAt(new Date())
                //fecha de vencimiento
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //firma del token
                .signWith(SECRETKEY)
                //Genera el token
                .compact();

    }

    public String getToken(String token) {
        return Jwts.parserBuilder()
                //Pasamos la clave secreta
                .setSigningKey(SECRETKEY)
                //Constuye el parser final para ser analizado
                .build()
                //analiza el token y lo devuelve en una lista
                .parseClaimsJws(token)
                //Obtenemos el Claim del parser
                .getBody()
                //obtenemos los datos guardados en el token
                .getSubject();
    }
}
