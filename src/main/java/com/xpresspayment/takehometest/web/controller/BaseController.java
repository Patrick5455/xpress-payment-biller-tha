
package com.xpresspayment.takehometest.web.controller;

import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.dto.auth.response.LoginResponse;
import com.xpresspayment.takehometest.common.dto.auth.account.AccountInfo;
import com.xpresspayment.takehometest.common.dto.web.response.ApiResponse;
import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.Data;

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

    public LoginResponse toLoginResponse(String authToken, Role role) {
        return LoginResponse.builder()
                .authToken(authToken)
                .role(role)
                .build();
    }

    public static AccountInfo toAccountInfo(UserDto userDto) {
        return AccountInfo.builder()
                .uuid(userDto.getUuid())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .createdAt(userDto.getCreatedAt().toString())
                .build();
    }


}
