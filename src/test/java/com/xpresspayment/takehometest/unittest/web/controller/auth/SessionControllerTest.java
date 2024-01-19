package com.xpresspayment.takehometest.unittest.web.controller.auth;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.configs.exceptions.RestExceptionHandler;
import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.dto.auth.request.LoginRequest;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import com.xpresspayment.takehometest.common.dto.auth.response.LoginResponse;
import com.xpresspayment.takehometest.common.dto.auth.response.SignUpResponse;
import com.xpresspayment.takehometest.common.enumconstants.Role;
import com.xpresspayment.takehometest.security.models.AuthOwnerDetails;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.UserService;
import com.xpresspayment.takehometest.security.services.i.AuthenticationService;
import com.xpresspayment.takehometest.services.SignupService;
import com.xpresspayment.takehometest.web.controller.auth.SessionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class SessionControllerTest extends AbstractTest {

    @Mock
    private SignupService signupService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;


    @InjectMocks
    private SessionController sessionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sessionController)
                .setControllerAdvice(new RestExceptionHandler()) // Add your exception handler here
                .build();
    }



    @Test
    void testUserSignupWithNonExistingEmail() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("jogndoe@gmail.com")
                .password("JohnDoe123#")
                .role(Role.ROLE_ORDINARY_USER)
                .build();

        SignUpResponse signUpResponse = new SignUpResponse("userId", "newuser@example.com");

        when(signupService.registerUser(any(SignUpRequest.class))).thenReturn(signUpResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/sessions/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
        );

         resultActions.andExpect(status().isCreated()).andReturn();
    }

    @Test
    void testUserSignupWithCorrectFields() throws Exception {
        SignUpRequest signUpRequest =  SignUpRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("jogndoe@gmail.com")
                .password("JohnDoe123#")
                .role(Role.ROLE_ORDINARY_USER)
                .build();
        SignUpResponse signUpResponse = new SignUpResponse("userId", "newuser@example.com");

        when(signupService.registerUser(any(SignUpRequest.class))).thenReturn(signUpResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/sessions/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
        );

        resultActions.andExpect(status().isCreated()).andReturn();
    }
}
