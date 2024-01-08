
package com.xpresspayment.takehometest.common.dto.account;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xpresspayment.takehometest.common.enumconstants.Role;
import com.xpresspayment.takehometest.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private String uuid;
    private String firstname;
    private String lastname;
    private String email;
    @JsonIgnore
    private String password;
    private boolean isActive;
    @Builder.Default
    private Role role = Role.ROLE_ORDINARY_USER;
    private Timestamp createdAt;


    public static UserDto toUserDto(UserEntity entity){
        return UserDto.builder()
                .uuid(entity.getUuid())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(Role.ROLE_ORDINARY_USER)
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .build();
    }


}
