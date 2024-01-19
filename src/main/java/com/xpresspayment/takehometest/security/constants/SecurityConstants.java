package com.xpresspayment.takehometest.security.constants;

public class SecurityConstants {

    public static final String SECRET = "XPRESS_ABC_XYZ_123_#";
    public static final  String ISSUER = "Revnorth";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final int AUTH_TOKEN_START_INDEX = 7;
    public static final String INVALID_TOKEN_ERROR_MESSAGE = "expired session: please login again to continue";

    public SecurityConstants() {}
}
