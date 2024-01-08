
package com.xpresspayment.takehometest.common.utils;

public abstract class Constants {

    public static final Integer WALLET_LIMIT = 10;
    public static final Integer PAGE_LIMIT = 10;
    public static  final String INTERNAL_SERVER_ERROR = "500: Internal Server Error";
    public static final String RESOURCE_NOT_FOUND = "404: Resource Not Found";
    public static final String BAD_REQUEST = "400: Client Bad Request";
    public static final String RESOURCE_CREATED = "201: Resource Created";
    public static final String REQUEST_OK = "200: Request OK";
    public static final String UNAUTHORIZED_ACCESS = "401: Unauthorized access";
    public static String APPLICATION_URL_FORM_ENCODED= "application/x-www-form-urlencoded";
    public static String APPLICATION_JSON = "application/json";
    public static final String RESET_PASSWORD_OTP = "reset_password_otp";
    public static final String RESET_PASSWORD_EXPIRY_DATE = "reset_password_expiry_date";
    public static final String RESET_PASSWORD_TOKEN = "RESET_PASSWORD_TOKEN";

    //EMAIL TEMPLATE RESOURCE PATHS
    public static final String CONFIRM_ACCOUNT_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/confirm-account.html";
    public static final String SIGNUP_OTP_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/signup-otp.html";
    public static final String _2FA_OTP_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/2fa-otp.html";

    public static final String PASSWORD_VALIDATION_MESSAGE = "Password not strong enough: " +
            "password must contain alpha-numeric characters (both lowercase and uppercase) and any of symbols (#,$,@,!) and must be" +
            " (8-20) characters long";
    public static final String PHONE_NUMBER_VALIDATION_MESSAGE = "Phone number is not valid, check country code and phone number length";
    public static final String RESOURCE_EXIST_VALIDATION_MESSAGE = "Resource does not exist";
    public static final String USER_DOES_NOT_EXIST_MESSAGE = "User does not exist";
    public static final String OTP_DOES_NOT_EXIST_MESSAGE = "Invalid or expired OTP";
    public static final String DEFAULT_TIMEZONE = "UTC";

}
