package com.xpresspayment.takehometest.unittest.web.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.configs.exceptions.RestExceptionHandler;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import com.xpresspayment.takehometest.common.dto.auth.response.SignUpResponse;
import com.xpresspayment.takehometest.common.enumconstants.Role;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
                .password("password")
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
                .password("password")
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

//    @Test
//    void testUserSignupWithExistingEmail() throws Exception {
//        SignUpRequest signUpRequest = SignUpRequest.builder()
//                .firstname("John")
//                .lastname("Doe")
//                .email("jogndoe@gmail.com")
//                .password("password")
//                .role(Role.ROLE_ORDINARY_USER)
//                .build();
//
//        when(signupService.registerUser(any(SignUpRequest.class)))
//                .thenThrow(new IllegalArgumentException("User with this email already exists"));
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/v1/sessions/sign-up")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(signUpRequest))
//        );
//
//        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest()).andReturn();
//        // Add more assertions based on your application's response
//    }


//    @Test
//    void testUserLoginWithCorrectCredentials() throws Exception {
//        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
//        LoginResponse loginResponse = new LoginResponse("jwtToken", Role.ROLE_ORDINARY_USER);
//
//        // Mock the authentication manager to return a valid authentication
//        when(authenticationManager.authenticate(any()))
//                .thenReturn(null);
//
//        // Mock the authentication service to return a JWT token
//        when(authenticationService.createAuthenticationToken(isA(UserPrincipal.class))).thenReturn("jwtToken");
//
//        // Mock the userService to return a non-null UserDto
//        UserDto userDto = new UserDto();
//        userDto.setEmail("user@example.com");
//        userDto.setRole(Role.ROLE_ORDINARY_USER);
//
//        when(userService.loadUserDtoByUsername("user@example.com")).thenReturn(userDto);
//
//        // Perform the login request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/v1/sessions/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(loginRequest))
//        );
//
//        // Assert the response status is OK
//        resultActions.andExpect(status().isOk());
//
//        // Assert JWT token and role
//        MvcResult mvcResult = resultActions.andReturn();
//        String authToken = mvcResult.getResponse().getHeader("Authorization");
//        AuthOwnerDetails authOwnerDetails = authenticationService.getAuthenticationOwnerDetails(authToken);
//
//        assertEquals("jwtToken", authToken, "JWT token is not correct");
//        assertEquals(Role.ROLE_ORDINARY_USER, authOwnerDetails.getUserRole(), "User role is not correct");
//    }
//
//
//    @Test
//    void testUserWithExpiredJwtCannotMakeAuthenticatedCalls() throws Exception {
//        // Mock a user with an expired JWT token
//        when(authenticationService.isValidAuthenticationToken(any())).thenReturn(false);
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .get("/v1/sessions")
//                .header("Authorization", "expiredJwtToken")
//        );
//
//        MvcResult mvcResult = resultActions.andExpect(status().isUnauthorized()).andReturn();
//        // Add more assertions based on your application's response
//    }
//
//    @Test
//    void testLoggedInUserCanLoginSuccessfully() throws Exception {
//        // Mock a user with a valid JWT token
//        when(authenticationService.isValidAuthenticationToken(any())).thenReturn(true);
//        when(authenticationService.getAuthenticationOwnerDetails(any())).thenReturn(
//                AuthOwnerDetails.builder()
//                        .username("user@example.com")
//                        .authExpiredAt(LocalDateTime.now())
//                        .authExpiredAt(LocalDateTime.now().plusHours(1))
//                        .build());
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .get("/v1/sessions")
//                .header("Authorization", "validJwtToken")
//        );
//
//        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
//        // Add more assertions based on your application's response
//    }
}
