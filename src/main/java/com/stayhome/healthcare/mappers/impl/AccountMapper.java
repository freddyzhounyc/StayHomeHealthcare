package com.stayhome.healthcare.mappers.impl;

import com.stayhome.healthcare.domain.dto.AccountDto;
import com.stayhome.healthcare.domain.entities.Account;
import com.stayhome.healthcare.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountDto> {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto mapTo(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    public Account mapFrom(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }

}
