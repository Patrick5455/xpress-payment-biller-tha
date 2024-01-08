package com.xpresspayment.takehometest.unittest.services;

import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Slf4j

class SignupServiceTest extends AbstractTest {

    private static SignUpRequest signUpRequest1;
    private static SignUpRequest signUpRequest2;

    @BeforeAll
    static void beforeAll() {
        log.info("before all");

      signUpRequest1 =  SignUpRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("johndoe@gmail.com")
                .password("password")
                .build();

        signUpRequest2 =  SignUpRequest.builder()
                .firstname("Jane")
                .lastname("Doe")
                .email("janedoe@gmail.com")
                .password("password")
                .build();
    }

//    @Test
//    @DisplayName("user with no existing email should be able to register")
//    void testRegisterUserWithNonExistingEmail() {
//      signupService.registerUser(signUpRequest1);
//      assertDoesNotThrow(() -> signupService.registerUser(signUpRequest1),
//              "user with no existing email should be able to register");
//    }
}