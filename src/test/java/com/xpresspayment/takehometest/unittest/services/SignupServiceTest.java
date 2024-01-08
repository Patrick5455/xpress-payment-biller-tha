package com.xpresspayment.takehometest.unittest.services;

import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j

class SignupServiceTest extends AbstractTest {

    private SignUpRequest signUpRequest1;
    private SignUpRequest signUpRequest2;


    @BeforeEach
    void setUp() {

            signUpRequest1 =  SignUpRequest.builder()
                    .firstname("John")
                    .lastname("Doe")
                    .email(String.format("johndoe.%s@gmail.com", System.currentTimeMillis()))
                    .password("password")
                    .build();

            signUpRequest2 =  SignUpRequest.builder()
                    .firstname("Jane")
                    .lastname("Doe")
                    .email(String.format("janedoe.%s@gmail.com", System.currentTimeMillis()))
                    .password("password")
                    .build();
        }

    @Test
    @DisplayName("user with no existing email should be able to register")
    void testRegisterUserWithNonExistingEmail() {
      assertDoesNotThrow(() -> signupService.registerUser(signUpRequest1),
              "user with no existing email should be able to register");
    }

    @Test
    @DisplayName("user with existing email should not be able to register")
    void testRegisterUserWithExistingEmail() {
        signupService.registerUser(signUpRequest2);
      assertThrows(AppException.class, () -> signupService.registerUser(signUpRequest2),
              "user with existing email should not be able to register");
    }




}