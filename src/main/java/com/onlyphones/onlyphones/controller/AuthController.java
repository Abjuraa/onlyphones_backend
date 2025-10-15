package com.onlyphones.onlyphones.controller;

import com.onlyphones.onlyphones.dto.LoginRequest;
import com.onlyphones.onlyphones.dto.LoginResponse;
import com.onlyphones.onlyphones.exceptions.AuthException;
import com.onlyphones.onlyphones.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
      try {
          LoginResponse response = authService.login(loginRequest);
          return ResponseEntity.ok(response);
      } catch (AuthException e) {
          return ResponseEntity.status(401).body(new LoginResponse(null, e.getMessage()));
      }
    }


}
