
package com.xpresspayment.takehometest.common.configs.exceptions;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.xpresspayment.takehometest.common.dto.web.response.ApiResponse;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.exceptions.InvalidTokenException;
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
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ApiResponse> handleAllExceptions(AppException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(ex.getCode().getCode())
                        .message(ex.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> handleInvalidMethodArgument(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
        return  ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message(errors.toString())
                            .code(HttpStatusCode.STATUS_400.getCode())
                            .success(false)
                            .build()
            );
        }



    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse> handleIHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("invalid request: please check your request")
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<ApiResponse> handleInvalidTokenException(InvalidTokenException ex) {
        log.error("InvalidTokenException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("you've been logged out, please login again to continue ")
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("AuthenticationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("you've been logged out, please login again to continue:")
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }


    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ApiResponse> handleValidationExceptionException(ValidationException ex) {
        log.error("ValidationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("invalid request, please check your request")
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message(
                                ex.getConstraintViolations().stream().map(
                                                ConstraintViolation::getMessage)
                                        .toList().get(0))
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }

    @ExceptionHandler(value
            = {UsernameNotFoundException.class})
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException:",ex);
        return  ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .message("Oops!, please sign up to continue")
                        .code(HttpStatusCode.STATUS_400.getCode())
                        .success(false)
                        .build()
        );
    }


}

