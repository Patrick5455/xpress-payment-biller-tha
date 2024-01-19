package com.xpresspayment.takehometest.unittest.web.controller.auth;

import java.sql.Timestamp;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @Mock
    private UserPrincipal userPrincipal;

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
                .post("/v1/sessions/sign-up").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
        );

        resultActions.andExpect(status().isCreated()).andReturn();
    }

    @Test
    void testUserSignupWithCorrectFields() throws Exception {
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
                .post("/v1/sessions/sign-up").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequest))
        );

        resultActions.andExpect(status().isCreated()).andReturn();
    }

//    @Test
//    void testUserLoginWithCorrectCredentials() throws Exception {
//        //test user login with correct credentials
//
//
//        LoginRequest loginRequest = LoginRequest.builder()
//                .username("johndoe@gmail.com")
//                .password("JohnDoe123#")
//                .build();
//
//        UserDto userDto = UserDto.builder()
//                .uuid("userId")
//                .firstname("John")
//                .lastname("Doe")
//                .email("")
//                .password("")
//                .isActive(true)
//                .role(Role.ROLE_ORDINARY_USER)
//                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        LoginResponse loginResponse = LoginResponse.builder()
//                .authToken("token")
//                .role(Role.ROLE_ORDINARY_USER)
//                .build();
//
//        UserPrincipal userPrincipal = UserPrincipal.createUser(userDto);
//
//        when(authenticationManager.authenticate(any())).thenReturn(null);
//        when(userService.loadUserByUsername(loginRequest.getUsername())).thenReturn(userPrincipal);
//        when(userService.loadUserDtoByUsername(loginRequest.getUsername())).thenReturn(userDto);
//        when(userService.getLoggedInUser()).thenReturn(userDto);
//        when(authenticationService.createAuthenticationToken(userPrincipal)).thenReturn("token");
//        when(authenticationService.createAuthenticationToken(any(Authentication.class))).thenReturn("token");
//        when(authenticationService.getAuthenticationOwnerDetails(any())).thenReturn(
//                AuthOwnerDetails.builder()
//                        .authExpiredAt(LocalDateTime.now())
//                        .authIssuedAt(LocalDateTime.now())
//                        .username("")
//                        .userRole(Role.ROLE_ORDINARY_USER)
//                        .build());
//        when(userPrincipal.createUser(any(UserDto.class))).thenReturn(userPrincipal);
//        when(authenticationService.isValidAuthenticationToken(anyString())).thenReturn(true);
//
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/v1/sessions/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(loginRequest))
//        );
//
//        resultActions.andExpect(status().isOk()).andReturn();
//    }
}
