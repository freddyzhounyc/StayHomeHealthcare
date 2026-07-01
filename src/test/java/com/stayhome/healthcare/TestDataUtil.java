package com.stayhome.healthcare;

import com.stayhome.healthcare.domain.dto.auth.AuthRequest;
import com.stayhome.healthcare.domain.dto.auth.RegisterRequest;
import com.stayhome.healthcare.domain.entities.enums.Role;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static RegisterRequest createRegisterRequestA1() {
        return RegisterRequest.builder()
                .username("test1")
                .email("test1@gmail.com")
                .password("tT@12345")
                .role(Role.PATIENT)
                .build();
    }
    public static AuthRequest createAuthRequestA1() {
        return AuthRequest.builder()
                .email("test1@gmail.com")
                .password("tT@12345")
                .build();
    }

    public static RegisterRequest createRegisterRequestA2() {
        return RegisterRequest.builder()
                .username("tester")
                .email("test1@gmail.com")
                .password("tT@12345")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestA3() {
        return RegisterRequest.builder()
                .username("test1")
                .email("tester@gmail.com")
                .password("tT@12345")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestB() {
        return RegisterRequest.builder()
                .username("  myUsername  ")
                .email("     myEmail@gmail.com  ")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadEmailA() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadEmailB() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUsergmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadEmailC() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmailcom")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordA() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("MYPA$$W0RD")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordB() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("mypa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordC() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("myPa$$word")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordD() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("myPassw0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordE() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("myP$w0")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadPasswordF() {
        return RegisterRequest.builder()
                .username("myUser")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd_myPa$$w0rd_myPa$$w0rd_myPa$$w0rd_myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadUsernameA() {
        return RegisterRequest.builder()
                .username("")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadUsernameB() {
        return RegisterRequest.builder()
                .username("my")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadUsernameC() {
        return RegisterRequest.builder()
                .username("$$$")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadUsernameD() {
        return RegisterRequest.builder()
                .username("myUser_myUser_myUser_myUser_myUser_myUser")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }
    public static RegisterRequest createRegisterRequestWithBadUsernameE() {
        return RegisterRequest.builder()
                .username("myUser?")
                .email("myUser@gmail.com")
                .password("myPa$$w0rd")
                .role(Role.PATIENT)
                .build();
    }

}
