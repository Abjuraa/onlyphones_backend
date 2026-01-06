package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.dto.*;
import com.onlyphones.onlyphones.entity.Rol;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.exceptions.AuthException;
import com.onlyphones.onlyphones.repository.RolRepository;
import com.onlyphones.onlyphones.repository.UserRepository;
import com.onlyphones.onlyphones.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {


    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Correo electronico inexistente"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("La contraseña es incorrecta");
        }

        String token = jwtUtils.generateToken(user);
        return new AuthResponse(token, "inicio de sesion correcto");
    }

    public AuthResponse register(RegisterRequest request) {
        Rol rol = rolRepository.findByRol("Client").orElseThrow(() -> new RuntimeException("No se encontro el rol"));
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
             throw new AuthException("Usuario con correo ya registrado");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setUserRol(rol);

        userRepository.save(newUser);

        String token = jwtUtils.generateToken(newUser);

        return new AuthResponse(token, "Usuario creado correctamente");
    }

    public RecoverPasswordResponse recoverPassword(RecoverPasswordRequest data) {
        User user = userRepository.findByEmail(data.getEmail()).orElseThrow(() -> new RuntimeException("El correo ingresado no existe"));
        if(user != null) {
            user.setPassword(passwordEncoder.encode(data.getPassword()));
        }

        userRepository.save(user);

        return new RecoverPasswordResponse("Contraseña editada correctamente");
    }

    }



