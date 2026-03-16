package com.stayhome.healthcare.controllers;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.services.AccountService;
import com.stayhome.healthcare.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    // Dependency Injection
    public AuthController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Check if account already exists
        if (accountService.existsByEmail(request.getEmail()) || accountService.existsByUsername(request.getUsername())) {
            log.error("** User with that username or email already exists!! **");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Otherwise register user
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
