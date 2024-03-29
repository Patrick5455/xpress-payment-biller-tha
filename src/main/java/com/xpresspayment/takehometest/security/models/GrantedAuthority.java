

package com.xpresspayment.takehometest.security.models;

import java.io.Serial;

import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 540L;

    private Role role;

    @Override
    public String getAuthority() {
        return role.getAuthority().name();
    }
}
