package com.stayhome.healthcare.services;

import com.stayhome.healthcare.domain.entities.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Account> findOneById(UUID id);
}
