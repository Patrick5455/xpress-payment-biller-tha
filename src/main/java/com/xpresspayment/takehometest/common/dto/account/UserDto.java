
package com.xpresspayment.takehometest.commons.dto.account;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xpresspayment.takehometest.commons.enumconstants.Role;
import com.xpresspayment.takehometest.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    @JsonIgnoreProperties
    private long id;
    @JsonIgnoreProperties
    private String password;
    private String first_name;
    private String last_name;
    private String primary_phone;
    private String email;
    private boolean active;
    private boolean verified_phone;
    private boolean verified_email;
    private boolean approved;
    private String uuid;
    private Role role;
    private Timestamp created_at;


    public static UserDto toUserDto(UserEntity entity, String uuid){
        return UserDto.builder()
//                .verified_email(entity.isEmail_verified())
//                .verified_phone(entity.isPhone_verified())
//                .approved(entity.isApproved())
//                .active(entity.isActive())
//                .first_name(entity.getFirstName())
//                .last_name(entity.getLastName())
//                .email(entity.getEmail())
//                .primary_phone(entity.getPrimaryPhone())
//                .uuid(uuid)
//                .role(Role.getRoleByUserType(entity.getUserType()))
//                .user_type(entity.getUserType())
//                .created_at(entity.getCreatedAt())
                .build();
    }


    public static UserDto toUserDto(UserEntity entity){
        return UserDto.builder()
//                .verified_email(entity.isEmail_verified())
//                .verified_phone(entity.isPhone_verified())
//                .approved(entity.isApproved())
//                .active(entity.isActive())
//                .first_name(entity.getFirstName())
//                .last_name(entity.getLastName())
//                .email(entity.getEmail())
//                .primary_phone(entity.getPrimaryPhone())
//                .id(entity.getId())
//                .uuid(entity.getUuid())
//                .role(Role.getRoleByUserType(entity.getUserType()))
//                .user_type(entity.getUserType())
//                .created_at(entity.getCreatedAt())
                .build();
    }


}
