

package com.xpresspayment.takehometest.security.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpresspayment.takehometest.common.dto.web.response.ApiResponse;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
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
        log.error("authentication error: ", e);
        try (ServletServerHttpResponse res = new ServletServerHttpResponse(httpServletResponse)) {
            res.getBody().write(new ObjectMapper().writeValueAsBytes(
                    ApiResponse.builder()
                            .code(HttpStatusCode.STATUS_401.getCode())
                            .data(null)
                            .message(e.getLocalizedMessage())
                            .success(false)
                            .build()
            ));
            res.setStatusCode(HttpStatus.UNAUTHORIZED);
            res.flush();
        }
    }
}
