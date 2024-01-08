/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.middleware.security.models;

import java.io.Serial;

import com.xpresspayment.takehometest.commons.enumconstants.Role;
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
