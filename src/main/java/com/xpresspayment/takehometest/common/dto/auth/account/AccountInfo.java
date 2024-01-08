package com.xpresspayment.takehometest.common.dto.auth.account;

import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    private String uuid;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Role role;
    private String createdAt;
}
