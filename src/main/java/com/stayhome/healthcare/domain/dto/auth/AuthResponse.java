package com.stayhome.healthcare.domain.dto.auth;

import com.stayhome.healthcare.domain.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private UUID id;
    private String username;
    private String email;
    private Role role;

}
