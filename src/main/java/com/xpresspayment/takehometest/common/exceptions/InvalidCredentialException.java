

package com.xpresspayment.takehometest.commons.exceptions;


import com.xpresspayment.takehometest.commons.enumconstants.HttpStatusCode;

public class InvalidCredentialException  extends AppException{

    public InvalidCredentialException (String message) {
        super(message, HttpStatusCode._400);
    }
}
