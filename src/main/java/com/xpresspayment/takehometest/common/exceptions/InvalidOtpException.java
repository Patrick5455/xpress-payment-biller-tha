
package com.xpresspayment.takehometest.commons.exceptions;


import com.xpresspayment.takehometest.commons.enumconstants.HttpStatusCode;

public class InvalidOtpException extends AppException {

    public InvalidOtpException(String message) {
        super(message, HttpStatusCode._400);
    }

    public InvalidOtpException(String message, Throwable cause){
        super(message, cause);
    }
}
