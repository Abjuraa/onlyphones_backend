package com.onlyphones.onlyphones.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

public class JwtUtils {

    private static final long EXPIRTAION_TIME = 1000*60*60;

    private final Key SECRETKEY = Keys.hmacShaKeyFor("constrasenasupersecretadetodalavida".getBytes());

    public String generateToken(String email, String rol) {

        return Jwts.builder()
                //a quien se le va a asignar el token
                .setSubject(email)
                //Tipo de rol o permisos asignados
                .claim("Client", rol)
                //fecha de creacion
                .setIssuedAt(new Date())
                //fecha de vencimiento
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRTAION_TIME))
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
