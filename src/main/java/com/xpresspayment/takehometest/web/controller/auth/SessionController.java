/*
 * Copyright (c) 2021.
 * Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 * Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 * confidential
 * Written by Patrick Ojunde <p@revnorth.io>
 */

package io.revnorth.web.controllers.auth;

import static io.revnorth.security.constants.SecurityConstants.AUTH_HEADER_KEY;
import static io.revnorth.security.constants.SecurityConstants.AUTH_TOKEN_START_INDEX;
import io.revnorth.account.services.onboarding.SignupService;
import io.revnorth.common.configs.GlobalConfig;
import io.revnorth.dto.account.BetaUserDto;
import io.revnorth.dto.account.UserDto;
import io.revnorth.enumconstant.HttpStatusCode;
import io.revnorth.exception.InvalidCredentialException;
import io.revnorth.security.models.UserPrincipal;
import io.revnorth.security.services.i.AuthenticationService;
import io.revnorth.security.services.i.IUserService;
import io.revnorth.web.controllers.BaseController;
import io.revnorth.web.utils.OnBoardingUtil;
import io.revnorth.webdtos.request.auth.LoginRequest;
import io.revnorth.webdtos.request.auth.SignUpRequest;
import io.revnorth.webdtos.response.auth.LoginResponse;
import io.revnorth.webdtos.response.auth.SignUpResponse;
import io.revnorth.webdtos.response.auth.account.AccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sessions")
@Slf4j
public class SessionController extends BaseController {

    private  final SignupService signupService;
    private  final OnBoardingUtil onBoardingUtil;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final AuthenticationService authenticationService;
    private final GlobalConfig globalConfig;

    @Autowired
    public SessionController(SignupService signupService,
                             OnBoardingUtil onBoardingUtil,
                             AuthenticationManager authenticationManager,
                             IUserService userService,
                             AuthenticationService authenticationService,
                             GlobalConfig globalConfig) {
        super();
        this.signupService = signupService;
        this.onBoardingUtil = onBoardingUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.globalConfig = globalConfig;
    }


    @Operation(
            summary = "Sign-up user",
            description = "This endpoint allows a guest  to signup as a user on the platform," +
                    "Account type include the following: " +
                    " SUPER_ADMIN, ADMIN, BUSINESS_PRINCIPAL, BUSINESS_MEMBER," +
                    " CORPORATE_CORPORATE_INVESTOR_PRINCIPAL, CORPORATE_CORPORATE_INVESTOR_MEMBER," +
                    " RETAIL_INVESTOR;" +
                    ""
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
    @PostMapping(value = "/sign-up", produces = {})
    public ResponseEntity<?> signupUser(
            @Valid  @RequestBody SignUpRequest request) {
        ResponseEntity<?> responseEntity;
        try {
            SignUpResponse response = signupService.registerUser(request);
            responseEntity = ResponseEntity.created(URI.create("")).
                    body(toApiResponse(response, "user successfully signed up",
                            HttpStatusCode._201.getCode(), true));
        }
        catch (Exception e){
            log.error("an exception occurred while registering user with email {}", request.getEmail(), e);
            responseEntity = ResponseEntity.badRequest().body(
                    toErrorResponse(e.getMessage(), HttpStatusCode._400.getCode())
            );
        }
       return responseEntity;
    }

    @Operation(
            summary = "Beta notify",
            description = "This endpoint allows a guest to submit email for beta notification"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
  @PostMapping(value = "/beta-notify", produces = {})
    public ResponseEntity<?> notifyAppLaunch(
            @Valid @RequestParam("email") @NotNull String email) {
        ResponseEntity<?> responseEntity;
        try {
            BetaUserDto betaUser = signupService.betaNotification(email);
            responseEntity = ResponseEntity.created(URI.create("")).
                    body(toApiResponse(betaUser, "beta-user successfully notified of app launch",
                            HttpStatusCode._201.getCode(), true));
        }
        catch (Exception e){
            log.error("could not process beta notification request", e);
            responseEntity = ResponseEntity.badRequest().body(
                    toErrorResponse(e.getMessage(), HttpStatusCode._400.getCode())
            );
        }
       return responseEntity;
    }


    @Operation(
            summary = "Login user",
            description = "This endpoint allows a guest with an account to log in as a user"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody LoginRequest request)
            throws InvalidCredentialException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
            return ResponseEntity.ok(
                    toApiResponse(getLoginResponse(
                                    request.getUsername()),
                            "user logged in successfully",
                            HttpStatusCode._200.getCode(), true));
        }
        catch (Exception e) {
            log.error("an error occurred during login attempt", e);
            return   ResponseEntity.badRequest().
                    body(toErrorResponse(e.getMessage(), HttpStatusCode._400.getCode()));
        }
    }



    @Operation(
            summary = "Get User Session Details",
            description = "This endpoint allows a client fetch details of the user in session"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
    @GetMapping()
    public ResponseEntity<?> getUserSessionDetails()
            throws InvalidCredentialException {
        try {
            return ResponseEntity.ok(
                    toApiResponse(getAccountInfo(userService.getLoggedInUser().getEmail()),
                            "user session details fetched successfully",
                            HttpStatusCode._200.getCode(), true));
        }
        catch (Exception e) {
            log.error("an error occurred during login attempt", e);
            return   ResponseEntity.badRequest().
                    body(toErrorResponse(e.getMessage(), HttpStatusCode._400.getCode()));
        }
    }


    @Operation(
            summary = "log out user",
            description = "This endpoint allows a user to be logged out of the application"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
    @GetMapping("/logout")
    public  ResponseEntity<?> logout (HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response , authentication);
            authenticationService.invalidateAuthenticationToken(getAuthToken(request));
            log.info("user logged out");
        }
        return ResponseEntity.ok(
                toApiResponse(null, "user logged out successfully",
                        HttpStatusCode._200.getCode(), true));
    }



    private String getAuthToken(HttpServletRequest request) {
        String authorizationScheme = request.getHeader(AUTH_HEADER_KEY);
        return authorizationScheme.substring(AUTH_TOKEN_START_INDEX);
    }

    private LoginResponse getLoginResponse(String username) {
        UserDto userDto = userService.loadUserDtoByUsername(username);
        UserPrincipal userPrincipal = UserPrincipal.createUser(userDto);
        return toLoginResponse(userDto,
                userDto.isVerified_email() ? authenticationService.createAuthenticationToken(userPrincipal) : "",
                globalConfig);
    }

    private AccountInfo getAccountInfo (String username) {
        UserDto userDto = userService.loadUserDtoByUsername(username);
        return toAccountInfo(userDto, onBoardingUtil.getByUserType(userDto));
    }


}
