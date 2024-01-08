

package com.xpresspayment.takehometest.common.exceptions;


import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;

public class InvalidCredentialException  extends AppException{

    public InvalidCredentialException (String message) {
        super(message, HttpStatusCode._400);
    }
}
