package com.xpresspayment.takehometest.commons.exceptions;

import com.xpresspayment.takehometest.commons.enumconstants.HttpStatusCode;
import lombok.Getter;

public class AppException extends RuntimeException {


    @Getter
    private HttpStatusCode code;
    // It cannot become final hence the NOSONAR
    private transient Object[] args;  //NOSONAR

    public AppException() {
        // default constructor
    }

    public AppException(String key, Object... args) {
        super(key);
        this.args = args;
    }

    public AppException(String key, Throwable cause, Object... args) {
        super(key, cause);
        this.args = args;
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public Object[] getArgs() {
        return args;
    }

}