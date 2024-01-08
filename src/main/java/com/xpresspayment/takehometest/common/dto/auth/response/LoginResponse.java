
package com.xpresspayment.takehometest.common.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String auth_token;
    private String redirectUrl;

}
