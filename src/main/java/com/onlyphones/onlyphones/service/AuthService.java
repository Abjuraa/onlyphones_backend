package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.dto.LoginRequest;
import com.onlyphones.onlyphones.dto.LoginResponse;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.repository.UserRepository;
import com.onlyphones.onlyphones.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getUserRol().getRol());
        return new LoginResponse(token, "inicio de sesion correcto");
    }
    }

