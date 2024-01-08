package com.xpresspayment.takehometest.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@EqualsAndHashCode(callSuper = true)
@Data @With @NoArgsConstructor @AllArgsConstructor
public class HttpCallException extends Exception {
    private int code;

    public HttpCallException(String message) {
        super(message);
        this.code = 400;
    }

    public HttpCallException(int code, String message) {
        super(message);
        this.code = code;
    }

    public HttpCallException(String message, Exception cause) {
        super(message, cause);
    }

    public HttpCallException(int code, String message, Exception cause) {
        super(message, cause);
        this.code = code;
    }

}