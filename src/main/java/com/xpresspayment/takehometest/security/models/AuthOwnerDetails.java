

package com.xpresspayment.takehometest.security.models;

import java.time.LocalDateTime;

import com.xpresspayment.takehometest.common.enumconstants.Role;
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
