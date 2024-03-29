

package com.xpresspayment.takehometest.security.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
