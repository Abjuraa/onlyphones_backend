package com.onlyphones.onlyphones.controller.auth;

import com.onlyphones.onlyphones.dto.LoginRequest;
import com.onlyphones.onlyphones.dto.AuthResponse;
import com.onlyphones.onlyphones.dto.RegisterRequest;
import com.onlyphones.onlyphones.exceptions.AuthException;
import com.onlyphones.onlyphones.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
      try {
          AuthResponse response = authService.login(loginRequest);
          return ResponseEntity.ok(response);
      } catch (AuthException e) {
          return ResponseEntity.status(401).body(new AuthResponse(null, e.getMessage()));
      }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            return  ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(new AuthResponse(null, e.getMessage()));
        }
    }


}
