
package com.xpresspayment.takehometest.commons.configs.exceptions;

import java.util.List;
import java.util.stream.Collectors;


import com.xpresspayment.takehometest.commons.dto.web.response.ApiResponse;
import com.xpresspayment.takehometest.commons.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.commons.exceptions.AppException;
import com.xpresspayment.takehometest.commons.exceptions.InvalidTokenException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler{

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<?> handleAllExceptions(AppException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(ex.getCode().getCode())
                        .message(ex.getLocalizedMessage())
                        .build());
    }

        @ExceptionHandler(value
                = {MethodArgumentNotValidException.class})
        public ResponseEntity<?> handleInvalidMethodArgument(MethodArgumentNotValidException ex) {
            List<String> errors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return  ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message(errors.toString())
                            .code(HttpStatusCode._400.getCode())
                            .success(false)
                            .build()
            );
        }



    @ExceptionHandler(value
            = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleIHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("invalid request: please check your request")
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value
            = {InvalidTokenException.class})
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex) {
        log.error("InvalidTokenException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("you've been logged out, please login again to continue ")
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value
            = {AuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
            log.error("AuthenticationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("you've been logged out, please login again to continue:")
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }


    @ExceptionHandler(value
            = {ValidationException.class})
    public ResponseEntity<?> handleValidationExceptionException(ValidationException ex) {
            log.error("ValidationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("invalid request, please check your request")
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message(
                                ex.getConstraintViolations().stream().map(
                                                ConstraintViolation::getMessage)
                                        .collect(Collectors.toList()).get(0))
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value
            = {UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("Oops!, please sign up to continue")
                        .code(HttpStatusCode._400.getCode())
                        .success(false)
                        .build()
        );
    }

    //ValidationException

}

