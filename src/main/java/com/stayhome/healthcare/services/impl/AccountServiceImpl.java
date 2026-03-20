package com.stayhome.healthcare.services.impl;

import com.stayhome.healthcare.domain.entities.Account;
import com.stayhome.healthcare.repositories.AccountRepository;
import com.stayhome.healthcare.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
    @Override
    public Optional<Account> findOneById(UUID id) {
        return accountRepository.findById(id);
    }

}
