

package com.xpresspayment.takehometest.security.models;

import java.io.Serial;

import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public class RevnorthGrantedAuthority implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 540L;

    private Role role;

    @Override
    public String getAuthority() {
        return role.getAuthority().name();
    }
}
