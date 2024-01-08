package com.xpresspayment.takehometest.common.dto.auth.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import com.xpresspayment.takehometest.common.annotations.constraints.ValidEnumValue;
import com.xpresspayment.takehometest.common.annotations.constraints.ValidPassword;
import com.xpresspayment.takehometest.common.enumconstants.Role;
import com.xpresspayment.takehometest.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.validation.annotation.Validated;

@Data @With @NoArgsConstructor @AllArgsConstructor
@Builder
@Validated
public class SignUpRequest {

    @NotBlank(message = "first name cannot be blank")
    private String firstname;
    @NotBlank(message = "last name cannot be blank")
    private String lastname;
    @NotBlank(message = "last name cannot be blank")
    @Email(message = "must be a valid email")
    @Valid
    private String email;
    @NotBlank(message = "password cannot be blank")
    @ValidPassword
    private String password;
    @Builder.Default
    @ValidEnumValue(enumClass = Role.class,
            message = "not a valid role")
    private Role role = Role.ROLE_ORDINARY_USER;


    public static UserEntity toUserEntity(SignUpRequest request, String uuid) {
        return UserEntity.builder()
                .uuid(uuid)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .isActive(false)
                .role(request.getRole())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
