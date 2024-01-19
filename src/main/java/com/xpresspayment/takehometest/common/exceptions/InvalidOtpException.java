
package com.xpresspayment.takehometest.common.exceptions;


import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;

public class InvalidOtpException extends AppException {

    public InvalidOtpException(String message) {
        super(message, HttpStatusCode.STATUS_400);
    }

    public InvalidOtpException(String message, Throwable cause){
        super(message, cause);
    }
}
