package com.xpresspayment.takehometest.commons.exceptions;


import com.xpresspayment.takehometest.commons.enumconstants.HttpStatusCode;

public class InvalidUuidException extends AppException{

    public InvalidUuidException (String message) {
        super(message, HttpStatusCode._400);
    }
}
