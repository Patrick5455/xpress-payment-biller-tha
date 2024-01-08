
package com.xpresspayment.takehometest.common.dto.auth.response;

import com.xpresspayment.takehometest.common.enumconstants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String authToken;
    /**
     * This is the user type which also determines the user's view
     */
    private Role role;

}
