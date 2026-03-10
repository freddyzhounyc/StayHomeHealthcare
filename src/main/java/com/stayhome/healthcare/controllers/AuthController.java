package com.stayhome.healthcare.controllers;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    // Dependency Injection
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
