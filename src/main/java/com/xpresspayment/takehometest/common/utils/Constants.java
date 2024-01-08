/*
 * Copyright (c) 2021.
 * Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 * Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 * confidential
 * Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.commons.utils;


import java.math.BigDecimal;

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

    public static final String REVNORTH_SYSTEM_EMAIL = "hello@revnorth.io";
    public static final String REVNORTH_INC_EMAIL_SENDER_NAME = "Revnorth Inc";
    public static final String REVNORTH_INC_EMAIL_HEADER_NAME = "Revnorth Inc";
    public static final String RESET_PASSWORD_OTP = "reset_password_otp";
    public static final String RESET_PASSWORD_EXPIRY_DATE = "reset_password_expiry_date";
    public static final String RESET_PASSWORD_TOKEN = "RESET_PASSWORD_TOKEN";

    //EMAIL TEMPLATE RESOURCE PATHS
    public static final String CONFIRM_ACCOUNT_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/confirm-account.html";
    public static final String SIGNUP_OTP_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/signup-otp.html";
    public static final String _2FA_OTP_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/2fa-otp.html";
    public static final String INVESTOR_INTEREST_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/interest-form-confirmation.html";

    public static final String INVESTOR_VISITOR_SIGNUP_INVITE_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/investor-visitor-signup-invite.html";
    public static final String BUSINESS_VISITOR_SIGNUP_INVITE_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/business-visitor-signup-invite.html";
    public static final String PASSWORD_RESET_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/reset-password.html";
    public static final String MAKER_REQUEST_EVENT_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/maker-request.html";
    public static final String CHECKER_RESPONSE_EVENT_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/checker-response.html";
    public static final String SYSTEM_ERROR_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/system-error.html";
    public static final String DEFAULT_BUSINESS_WALLET_DETAILS_EMAIL_TEMPLATE_RESOURCE_PATH = "templates/business-default-wallet-details.html";


    //offers
    public static final BigDecimal REVNORTH_COMMISSION = BigDecimal.valueOf(0.0125);

    //s3 bucket
    public static final String BUSINESS_KYB_UPLOAD_PATH = "business/business-kyb";
    public static final String BUSINESS_PRINCIPAL_KYC_UPLOAD_PATH = "business/business-principal-kyc";
    public static final String BUSINESS_MEMBER_KYC_UPLOAD_PATH = "business/business-member-kyc";
    public static final String BUSINESS_DIRECTOR_KYC_UPLOAD_PATH = "business/business-director-kyc";
    public static final String CORPORATE_INVESTOR_KYC_UPLOAD_PATH = "investor/corporate-investor-kyc";
    public static final String RETAIL_INVESTOR_KYC_UPLOAD_PATH = "investor/retail-investor-kyc";
    public static final String FINANCIAL_STATEMENT_UPLOAD_PATH = "business/financial-statement";
    public static final String BANK_STATEMENT_UPLOAD_PATH = "business/bank-statement";

    //annotations validation message
    public static final String CENT_VALUE_VALIDATION_MESSAGE = "must be a positive value less than 1";
    public static final String PASSWORD_VALIDATION_MESSAGE = "Password not strong enough: " +
            "password must contain alpha-numeric characters (both lowercase and uppercase) and any of symbols (#,$,@,!) and must be" +
            " (8-20) characters long";
    public static final String PHONE_NUMBER_VALIDATION_MESSAGE = "Phone number is not valid, check country code and phone number length";
    public static final String BUSINESS_EMAIL_VALIDATION_MESSAGE = "Email should have business domain and not domain " +
            "of public email providers like gmail";
    public static final String RESOURCE_EXIST_VALIDATION_MESSAGE = "Resource does not exist";
    public static final String USER_DOES_NOT_EXIST_MESSAGE = "User does not exist";
    public static final String OTP_DOES_NOT_EXIST_MESSAGE = "Invalid or expired OTP";
    public static final String BUSINESS_DOES_NOT_EXIST_MESSAGE = "Business does not exist";
    public static final String BUSINESS_USER_DOES_NOT_EXIST_MESSAGE = "User does not exist for this business";
    public static final String BUSINESS_OWNER_DOES_NOT_EXIST_MESSAGE = "User does not exist for this business";
    public static final String BUSINESS_MEMBER_DOES_NOT_EXIST_MESSAGE = "User does not exist for this business";
    public static final String DIRECTOR_DOES_NOT_EXIST_MESSAGE = "Director does not exist for this business";
    public static final String SYNDICATE_INVESTOR_DOES_NOT_EXIST_MESSAGE = "Syndicate investor does not exist for this business";
    public static final String RETAIL_INVESTOR_DOES_NOT_EXIST_MESSAGE = "Retail investor does not exist for this business";
    public static final String DOCUMENT_DOES_NOT_EXIST_MESSAGE = "Document does not exist for this business";
    public static final String DEFAULT_TIMEZONE = "UTC";

}
