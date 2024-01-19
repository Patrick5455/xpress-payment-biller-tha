
package com.xpresspayment.takehometest.web.controller.auth;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.dto.auth.account.AccountInfo;
import com.xpresspayment.takehometest.common.dto.auth.request.LoginRequest;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import com.xpresspayment.takehometest.common.dto.auth.response.LoginResponse;
import com.xpresspayment.takehometest.common.dto.auth.response.SignUpResponse;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.common.exceptions.InvalidCredentialException;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.i.AuthenticationService;
import com.xpresspayment.takehometest.security.services.i.IUserService;
import com.xpresspayment.takehometest.services.SignupService;
import com.xpresspayment.takehometest.web.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

import static com.xpresspayment.takehometest.security.constants.SecurityConstants.AUTH_HEADER_KEY;
import static com.xpresspayment.takehometest.security.constants.SecurityConstants.AUTH_TOKEN_START_INDEX;

@RestController
@RequestMapping("/v1/sessions")
@Slf4j
@AllArgsConstructor
public class SessionController extends BaseController {

    private  final SignupService signupService;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Sign-up user",
            description = "This endpoint allows a guest  to signup as a user on the platform," +
                    "Account type includes the following: " +
                    " SUPER_ADMIN, ADMIN, ORDINARY_USER"
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
    public ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> signupUser(
            @RequestBody  @Valid SignUpRequest request) {
        ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> responseEntity;
        try {
            SignUpResponse response = signupService.registerUser(request);
            responseEntity = ResponseEntity.created(URI.create("")).
                    body(toApiResponse(response, "user successfully signed up",
                            HttpStatusCode.STATUS_201.getCode(), true));
        }
        catch (Exception e){
            log.error("an exception occurred while registering user with email {}", request.getEmail(), e);
            responseEntity = ResponseEntity.badRequest().body(
                    toErrorResponse(e.getMessage(), HttpStatusCode.STATUS_400.getCode())
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
    public ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> loginUser(
            @RequestBody @Valid LoginRequest request)
            throws InvalidCredentialException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
            return ResponseEntity.ok(
                    toApiResponse(getLoginResponse(
                                    request.getUsername()),
                            "user logged in successfully",
                            HttpStatusCode.STATUS_200.getCode(), true));
        }
        catch (Exception e) {
            log.error("an error occurred during login attempt", e);
            return   ResponseEntity.badRequest().
                    body(toErrorResponse(e.getMessage(), HttpStatusCode.STATUS_400.getCode()));
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
    public ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> getUserSessionDetails()
            throws InvalidCredentialException {
        try {
            return ResponseEntity.ok(
                    toApiResponse(getAccountInfo(userService.getLoggedInUser().getEmail()),
                            "user session details fetched successfully",
                            HttpStatusCode.STATUS_200.getCode(), true));
        }
        catch (Exception e) {
            log.error("an error occurred during login attempt", e);
            return   ResponseEntity.badRequest().
                    body(toErrorResponse(e.getMessage(), HttpStatusCode.STATUS_400.getCode()));
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
    public  ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> logout (HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response , authentication);
            authenticationService.invalidateAuthenticationToken(getAuthToken(request));
            log.info("user logged out");
        }
        return ResponseEntity.ok(
                toApiResponse(null, "user logged out successfully",
                        HttpStatusCode.STATUS_200.getCode(), true));
    }


    private static String getAuthToken(HttpServletRequest request) {
        String authorizationScheme = request.getHeader(AUTH_HEADER_KEY);
        return authorizationScheme.substring(AUTH_TOKEN_START_INDEX);
    }

    private LoginResponse getLoginResponse(String username) {
        log.info("getting login response for user with username: {}", username);
        UserDto userDto = userService.loadUserDtoByUsername(username);
        UserPrincipal userPrincipal = UserPrincipal.createUser(userDto);
        return toLoginResponse(userDto.isActive() ?
                authenticationService.createAuthenticationToken(userPrincipal) : "", userDto.getRole());
    }

    private AccountInfo getAccountInfo (String username) {
        UserDto userDto = userService.loadUserDtoByUsername(username);
        return toAccountInfo(userDto);
    }

}
