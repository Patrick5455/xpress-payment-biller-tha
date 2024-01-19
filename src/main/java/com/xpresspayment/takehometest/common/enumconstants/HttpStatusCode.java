package com.xpresspayment.takehometest.common.enumconstants;

import static com.xpresspayment.takehometest.common.utils.Constants.BAD_REQUEST;
import static com.xpresspayment.takehometest.common.utils.Constants.INTERNAL_SERVER_ERROR;
import static com.xpresspayment.takehometest.common.utils.Constants.REQUEST_OK;
import static com.xpresspayment.takehometest.common.utils.Constants.RESOURCE_CREATED;
import static com.xpresspayment.takehometest.common.utils.Constants.RESOURCE_NOT_FOUND;
import static com.xpresspayment.takehometest.common.utils.Constants.UNAUTHORIZED_ACCESS;

public enum HttpStatusCode {

    STATUS_200(200, REQUEST_OK) {
        @Override
        public int getCode() {
            return STATUS_200.code;
        }

        @Override
        public String getMessage() {
            return STATUS_200.message;
        }
    },
    STATUS_201(201, RESOURCE_CREATED) {
        @Override
        public int getCode() {
            return STATUS_201.code;
        }

        @Override
        public String getMessage() {
            return STATUS_201.message;
        }
    },
    STATUS_400(400, BAD_REQUEST) {
        @Override
        public int getCode() {
            return STATUS_400.code;
        }

        @Override
        public String getMessage() {
            return STATUS_400.message;
        }
    },
    STATUS_404(404, RESOURCE_NOT_FOUND) {
        @Override
        public int getCode() {
            return STATUS_404.code;
        }

        @Override
        public String getMessage() {
            return STATUS_404.message;
        }
    },
    STATUS_401(401, UNAUTHORIZED_ACCESS) {
        @Override
        public int getCode() {
            return STATUS_401.code;
        }

        @Override
        public String getMessage() {
            return STATUS_404.message;
        }
    },
    STATUS_500(200, INTERNAL_SERVER_ERROR) {
        @Override
        public int getCode() {
            return STATUS_500.code;
        }

        @Override
        public String getMessage() {
            return STATUS_500.message;
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
