/*
 *
 *  * Copyright (c) 2021. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved
 *  *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *  *  confidential
 *  * Written by Patrick Ojunde <p@revnorth.io>, 05/22/2021
 *
 */

package com.xpresspayment.takehometest.middleware.security.constants;

public class SecurityConstants {

    public static final String SECRET = "ABC_XYZ_123_#";
    public static final  String ISSUER = "Revnorth";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final int AUTH_TOKEN_START_INDEX = 7;
    public static final String INVALID_TOKEN_ERROR_MESSAGE = "expired session: please login again to continue";
}
