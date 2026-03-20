package com.stayhome.healthcare.controllers;

import com.stayhome.healthcare.domain.dto.AccountDto;
import com.stayhome.healthcare.domain.entities.Account;
import com.stayhome.healthcare.mappers.Mapper;
import com.stayhome.healthcare.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class AccountController {

    private final AccountService accountService;
    private final Mapper<Account, AccountDto> accountMapper;

    public AccountController(AccountService accountService, Mapper<Account, AccountDto> accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountDto> readAccount(@PathVariable("id") UUID id) {
        Optional<Account> result = accountService.findOneById(id);
        return result.map(account -> new ResponseEntity<>(accountMapper.mapTo(account), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
