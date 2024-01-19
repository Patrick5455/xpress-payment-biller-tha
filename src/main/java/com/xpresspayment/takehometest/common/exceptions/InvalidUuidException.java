package com.xpresspayment.takehometest.common.exceptions;


import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;

public class InvalidUuidException extends AppException{

    public InvalidUuidException (String message) {
        super(message, HttpStatusCode.STATUS_400);
    }
}
