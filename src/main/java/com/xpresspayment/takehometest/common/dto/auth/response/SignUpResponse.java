

package com.xpresspayment.takehometest.common.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data @With @NoArgsConstructor @AllArgsConstructor
@Builder
public class SignUpResponse {
    private String token;
    private String email;
    private String account_type;
}
