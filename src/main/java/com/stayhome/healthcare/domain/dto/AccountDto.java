package com.stayhome.healthcare.domain.dto;

import com.stayhome.healthcare.domain.entities.Profile;
import com.stayhome.healthcare.domain.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

// pojo
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private UUID accountId;
//    private ProfileDto profile;
    private String username;
    private String email;
    private String password;
    private Role role;

}
