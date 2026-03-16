package com.stayhome.healthcare.services;

public interface AccountService {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
