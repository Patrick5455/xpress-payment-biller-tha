
package com.xpresspayment.takehometest.common.dto.auth.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Data @With @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
