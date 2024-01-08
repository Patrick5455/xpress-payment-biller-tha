/*
 * Copyright (c) 2021.
 * Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 * Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 * confidential
 * Written by Patrick Ojunde <p@revnorth.io>
 */

package io.revnorth.web.controllers;

import io.revnorth.common.configs.GlobalConfig;
import io.revnorth.dto.account.UserDto;
import io.revnorth.enumconstant.UserType;
import io.revnorth.exception.RevNorthApiException;
import io.revnorth.webdtos.response.ApiResponse;
import io.revnorth.webdtos.response.auth.LoginResponse;
import io.revnorth.webdtos.response.auth.account.AccountInfo;
import io.revnorth.webdtos.response.auth.account.OnBoardingDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class BaseController {

    public ApiResponse toApiResponse(Object data, String message, int code, boolean success) {
        return ApiResponse.builder()
                .data(data)
                .message(message)
                .code(code)
                .success(success)
                .build();
    }

    public ApiResponse toApiResponse(String message, int code, boolean success) {
        return ApiResponse.builder()
                .message(message)
                .code(code)
                .success(success)
                .build();
    }

    public ApiResponse toErrorResponse(String message, int code){
        return ApiResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }

    public LoginResponse toLoginResponse(UserDto userDto, String authToken, GlobalConfig globalConfig) {
        return LoginResponse.builder()
                .auth_token(authToken)
                .user_type(userDto.getUser_type())
                .redirectUrl(getRedirectUrl(userDto.getUser_type(), globalConfig))
                .build();
    }

    public static AccountInfo toAccountInfo(UserDto userDto, OnBoardingDetails onBoardingDetails) {
        return AccountInfo.builder()
                .first_name(userDto.getFirst_name())
                .last_name(userDto.getLast_name())
                .email(userDto.getEmail())
                .phone(userDto.getPrimary_phone())
                .user_type(userDto.getUser_type())
                .onBoarding_details(onBoardingDetails)
                .created_at(userDto.getCreated_at().toString())
                .build();
    }

    private String getRedirectUrl(UserType userType, GlobalConfig globalConfig) {
        switch (userType) {
            case BUSINESS_PRINCIPAL:
            case BUSINESS_MEMBER:
                return globalConfig.getSsoBusinessRedirectUrl();
            case RETAIL_INVESTOR:
            case CORPORATE_INVESTOR_PRINCIPAL:
            case CORPORATE_INVESTOR_MEMBER:
                return globalConfig.getSsoInvestorRedirectUrl();
            case SUPER_ADMIN:
            case ADMIN:
                return globalConfig.getSsoAdminRedirectUrl();
        }
        throw new RevNorthApiException("user not found");
    }



}
