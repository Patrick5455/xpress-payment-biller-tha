package com.xpresspayment.takehometest.common.dto.auth.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import com.xpresspayment.takehometest.common.annotations.constraints.ValidEnumValue;
import com.xpresspayment.takehometest.common.annotations.constraints.ValidPassword;
import com.xpresspayment.takehometest.common.enumconstants.Role;
import com.xpresspayment.takehometest.domain.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data @With @NoArgsConstructor @AllArgsConstructor
@Builder
public class SignUpRequest {

    @NotBlank(message = "first name cannot be blank")
    private String firstname;
    @NotBlank(message = "last name cannot be blank")
    private String lastname;
    @Email(message = "must be a valid email")
    @NotBlank(message = "email cannot be blank")
    private String email;
    @ValidPassword
    @NotBlank(message = "password cannot be blank")
    private String password;
    @Builder.Default
//    @ValidEnumValue(enumClass = Role.class,
//            message = "not a valid role")
    private Role role = Role.ROLE_ORDINARY_USER;


    public static UserEntity toUserEntity(SignUpRequest request, String uuid) {
        return UserEntity.builder()
                .uuid(uuid)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .isActive(true)
                .role(request.getRole())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
