package com.stayhome.healthcare;

import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.domain.entities.enums.Role;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static RegisterRequest createRegisterRequestA1() {
        return RegisterRequest.builder()
                .username("test1")
                .email("test1@gmail.com")
                .password("t@123")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestA2() {
        return RegisterRequest.builder()
                .username("tester")
                .email("test1@gmail.com")
                .password("t@123")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestA3() {
        return RegisterRequest.builder()
                .username("test1")
                .email("tester@gmail.com")
                .password("t@123")
                .role(Role.PATIENT)
                .build();
    }

}
