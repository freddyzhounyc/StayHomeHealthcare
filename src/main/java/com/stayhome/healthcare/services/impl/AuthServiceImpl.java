package com.stayhome.healthcare.services.impl;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.domain.entities.Account;
import com.stayhome.healthcare.repositories.AccountRepository;
import com.stayhome.healthcare.services.AuthService;
import com.stayhome.healthcare.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Dependency Injection
    public AuthServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Account account = Account.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        Account savedAccount = accountRepository.save(account);
        String jwt = jwtService.generateToken(account);
        return AuthResponse.builder()
                .token(jwt)
                .id(savedAccount.getAccountId())
                .username(savedAccount.getTheUsername())
                .email(savedAccount.getEmail())
                .role(savedAccount.getRole())
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // If the auth manager's authenticate method has not thrown an exception, then user is authenticated.
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(account);
        return AuthResponse.builder()
                .token(jwt)
                .id(account.getAccountId())
                .username(account.getTheUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .build();
    }

    @Override
    public boolean verifyPassword(String password) {
        if (password == null)
            return false;
        boolean hasReqLength = password.length() >= 8 && password.length() <= 50;
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return hasReqLength && hasLowercase && hasUppercase && hasNumber && hasSpecial;
    }

    @Override
    public boolean verifyEmail(String email) {
        if (email == null)
            return false;
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"); // ^[^\s@]+@[^\s@]+\.[^\s@]+$ (js/ts)
    }

    @Override
    public boolean verifyUsername(String username) {
        if (username == null)
            return false;
        return username.matches("^[a-zA-Z0-9_.]{3,20}$"); // ^[a-zA-Z0-9_.]{3,20}$
    }

}
