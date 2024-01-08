/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.middleware.security.services;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException {
//        log.error("authentication error: ", e);
//        ServletServerHttpResponse res = new ServletServerHttpResponse(httpServletResponse);
//        res.getBody().write(new ObjectMapper().writeValueAsBytes(
//                        ApiResponse.builder()
//                                .code(HttpStatusCode._401.getCode())
//                                .data(null)
//                                .message(e.getLocalizedMessage())
//                                .success(false)
//                                .build()
//                ));
//        res.setStatusCode(HttpStatus.UNAUTHORIZED);
//        res.flush();
    }
}
