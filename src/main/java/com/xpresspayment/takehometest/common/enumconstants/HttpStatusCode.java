package com.xpresspayment.takehometest.common.enumconstants;

import static com.xpresspayment.takehometest.common.utils.Constants.BAD_REQUEST;
import static com.xpresspayment.takehometest.common.utils.Constants.INTERNAL_SERVER_ERROR;
import static com.xpresspayment.takehometest.common.utils.Constants.REQUEST_OK;
import static com.xpresspayment.takehometest.common.utils.Constants.RESOURCE_CREATED;
import static com.xpresspayment.takehometest.common.utils.Constants.RESOURCE_NOT_FOUND;
import static com.xpresspayment.takehometest.common.utils.Constants.UNAUTHORIZED_ACCESS;

;

public enum HttpStatusCode {

    _200 (200, REQUEST_OK) {
        @Override
        public int getCode() {
            return _200.code;
        }

        @Override
        public String getMessage() {
            return _200.message;
        }
    },
    _201 (201, RESOURCE_CREATED) {
        @Override
        public int getCode() {
            return _201.code;
        }

        @Override
        public String getMessage() {
            return _201.message;
        }
    },
    _400 (400, BAD_REQUEST) {
        @Override
        public int getCode() {
            return _400.code;
        }

        @Override
        public String getMessage() {
            return _400.message;
        }
    },
    _404 (404, RESOURCE_NOT_FOUND) {
        @Override
        public int getCode() {
            return _404.code;
        }

        @Override
        public String getMessage() {
            return _404.message;
        }
    },
    _401 (401, UNAUTHORIZED_ACCESS) {
        @Override
        public int getCode() {
            return _401.code;
        }

        @Override
        public String getMessage() {
            return _404.message;
        }
    },
    _500 (200, INTERNAL_SERVER_ERROR) {
        @Override
        public int getCode() {
            return _500.code;
        }

        @Override
        public String getMessage() {
            return _500.message;
        }
    };


    private final int code;
    private final String message;

     HttpStatusCode(int code , String message){
        this.code = code;
        this.message = message;
    }

    public abstract int getCode();

    public abstract String getMessage();

}
