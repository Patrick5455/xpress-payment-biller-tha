/*
 * Copyright (c) 2021-2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package io.revnorth.webdtos.request.auth;

import io.revnorth.commonannotations.constraints.ValidEnumValue;
import io.revnorth.commonannotations.constraints.ValidPassword;
import io.revnorth.commonannotations.constraints.ValidPhoneNumber;
import io.revnorth.domain.entity.account.UserEntity;
import io.revnorth.enumconstant.Role;
import io.revnorth.enumconstant.UserType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data @With @NoArgsConstructor @AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "first name cannot be blank")
    private String first_name;
    @NotBlank(message = "last name cannot be blank")
    private String last_name;
    @NotBlank(message = "last name cannot be blank")
    @Email(message = "must be a valid email")
    private String email;
    @NotBlank(message = "phone cannot be blank")
    @ValidPhoneNumber
    private String phone_number;
    @NotBlank(message = "password cannot be blank")
    @ValidPassword
    private String password;
    @ValidEnumValue(enumClass = UserType.class,
            message = "not a valid account type")
    private String user_type;

    public static UserEntity toUserEntity(SignUpRequest request) {
        return UserEntity.builder()
                .firstName(request.getFirst_name())
                .lastName(request.getLast_name())
                .email(request.getEmail())
                .primaryPhone(request.getPhone_number())
                .userType(UserType.valueOf(request.user_type))
                .role(Role.getRoleByUserType(UserType.valueOf(request.user_type)))
                .active(false)
                .approved(false)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .password(request.getPassword())
                .build();
    }
}
