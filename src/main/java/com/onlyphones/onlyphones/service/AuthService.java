package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.dto.LoginRequest;
import com.onlyphones.onlyphones.dto.LoginResponse;
import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.exceptions.AuthException;
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

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Credenciales invalidas");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getUserRol().getRol());
        return new LoginResponse(token, "inicio de sesion correcto");
    }
    }

