/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.middleware.security.models;

import java.time.LocalDateTime;

import com.xpresspayment.takehometest.commons.enumconstants.Role;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Builder
@Data
@With
public class AuthOwnerDetails {
    private String username;
    private Role userRole;
    private LocalDateTime authIssuedAt;
    private LocalDateTime authExpiredAt;
}
