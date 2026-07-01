package com.stayhome.healthcare.controllers;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.services.AccountService;
import com.stayhome.healthcare.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    private HttpHeaders createTokenCookieAndHeader(String token) {
        // Create cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax") // csrf
                .maxAge(60 * 60 * 24) // 24-hour lifespan
                .path("/api") // to be sent to all paths
                .build();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Trim username and emails if not null
        if (request.getUsername() != null) request.setUsername(request.getUsername().trim());
        if (request.getEmail() != null) request.setEmail(request.getEmail().trim());

        // Verification 1: Password verification
        if (!authService.verifyPassword(request.getPassword())) {
            log.error("** Password in request does not follow requirements! **");
            AuthResponse response = AuthResponse.builder()
                    .message("Password does not follow requirements")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Verification 2: Email verification
        // TODO: Ultimately will have to have user verify email via sending verification email, but for when app is more established
        if (!authService.verifyEmail(request.getEmail())) {
            log.error("** Email in request is not a valid email! **");
            AuthResponse response = AuthResponse.builder()
                    .message("Email is not valid")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Verification 3: Username verification
        if (!authService.verifyUsername(request.getUsername())) {
            log.error("** Username in request is not valid! **");
            AuthResponse response = AuthResponse.builder()
                    .message("Username is not valid")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Verification 4: Check if account or email already exists
        boolean emailExists = accountService.existsByEmail(request.getEmail());
        boolean accountExists = accountService.existsByUsername(request.getUsername());
        if (emailExists || accountExists) {
            log.error("** User with that username or email already exists!! **");
            AuthResponse response = AuthResponse.builder()
                    .message(emailExists ? "User with that email already exists" : "User with that username already exists")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        AuthResponse response = authService.register(request); // Request is VALID, so register user
        HttpHeaders headers = createTokenCookieAndHeader(response.getToken()); // Create cookie and header
        response.setToken(null); // Delete token to prevent XSS
        return new ResponseEntity<>(response, headers, HttpStatus.OK); // Register user
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request); // Authenticate login credentials
        HttpHeaders headers = createTokenCookieAndHeader(response.getToken()); // User is authenticated, so create cookie and header
        response.setToken(null); // Delete token to prevent XSS
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

}
