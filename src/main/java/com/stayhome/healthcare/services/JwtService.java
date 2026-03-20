package com.stayhome.healthcare.services;

import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String extractUsername(String jwt);
    Date extractExpiration(String jwt);
    boolean isTokenValid(String jwt, UserDetails userDetails);
    boolean isTokenExpired(String jwt);

}
