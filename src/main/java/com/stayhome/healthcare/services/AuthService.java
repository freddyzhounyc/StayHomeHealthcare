package com.stayhome.healthcare.services;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.AuthResponse;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);

}
