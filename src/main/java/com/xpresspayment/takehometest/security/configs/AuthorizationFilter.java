
package com.xpresspayment.takehometest.security.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.common.dto.web.response.ApiResponse;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.common.exceptions.InvalidTokenException;
import com.xpresspayment.takehometest.common.utils.i.CachingService;
import com.xpresspayment.takehometest.security.models.AuthOwnerDetails;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.i.AuthenticationService;
import com.xpresspayment.takehometest.security.services.i.IUserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.xpresspayment.takehometest.security.constants.SecurityConstants.*;


@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {


    private final IUserService userService;
    private final AuthenticationService authenticationService;
    private final CachingService<String, Object> redisCaching;

    public AuthorizationFilter(IUserService userService,
                               AuthenticationService authenticationService,
                               @Qualifier("redis") CachingService<String, Object> redisCaching) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.redisCaching = redisCaching;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException, InvalidTokenException {
        String authorizationScheme = httpServletRequest.getHeader(AUTH_HEADER_KEY);
        String authToken;
        AuthOwnerDetails authOwnerDetails = null;
        if (authorizationScheme != null && authorizationScheme.startsWith(AUTH_TOKEN_PREFIX)) {
            authToken = authorizationScheme.substring(AUTH_TOKEN_START_INDEX);
             if (!authToken.isEmpty()) {
                 try {
                     authOwnerDetails = authenticationService.getAuthenticationOwnerDetails(authToken);
                 } catch (InvalidTokenException e) {
                     httpServletResponse.addHeader("Content-Type", "application/json");
                     httpServletResponse.setStatus(440);
                     httpServletResponse.getWriter().write(
                             new Gson().toJson(
                                     ApiResponse.builder()
                                             .success(false)
                                             .code(440)
                                             .message(e.getLocalizedMessage())
                                             .build()));

                     return;
                 }
             }
             try {
                doAuthorizeUser(authOwnerDetails, httpServletRequest, authToken);
             } catch (Exception e) {
                httpServletResponse.addHeader("Content-Type", "application/json");
                if (e instanceof InvalidTokenException) {
                    httpServletResponse.setStatus(440);
                    httpServletResponse.getWriter().write(
                             new Gson().toJson(
                                     ApiResponse.builder()
                                             .success(false)
                                             .code(440)
                                             .message(e.getLocalizedMessage())
                                             .build()));

                 } else {
                    httpServletResponse.setStatus(400);
                    httpServletResponse.getWriter().write(
                             new Gson().toJson(
                                     ApiResponse.builder()
                                             .success(false)
                                             .code(HttpStatusCode._400.getCode())
                                             .message(e.getLocalizedMessage())
                                             .build()));
                 }
                return;

             }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }



    private void doAuthorizeUser(AuthOwnerDetails authOwnerDetails,
                                HttpServletRequest httpServletRequest,
                                String authToken) throws InvalidTokenException{
        if(authOwnerDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (authenticationService.isValidAuthenticationToken(authToken)) {
                Authentication authentication = getAuthentication(
                        httpServletRequest, authOwnerDetails, authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                redisCaching.saveOrUpdate(authOwnerDetails.getUsername(), userService.getLoggedInUser());
            } else {
                throw new InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE);
            }
        }
    }

    private Authentication getAuthentication(HttpServletRequest httpServletRequest,
                                             AuthOwnerDetails authOwnerDetails, String authToken) {
        UserPrincipal userPrincipal;
        try {
            userPrincipal = (UserPrincipal)
                    userService.loadUserByUsername(authOwnerDetails.getUsername());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userPrincipal, authToken,
                            userPrincipal.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(httpServletRequest));
            return usernamePasswordAuthenticationToken;
        } catch (UsernameNotFoundException ex) {
            log.error("username not found", ex);
            throw new UsernameNotFoundException("Oops!, please sign up to continue");
        }
    }
}
