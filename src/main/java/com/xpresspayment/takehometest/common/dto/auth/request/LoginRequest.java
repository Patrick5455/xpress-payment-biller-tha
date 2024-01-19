
package com.xpresspayment.takehometest.common.dto.auth.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.xpresspayment.takehometest.common.annotations.constraints.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Data @With @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {

    @Email(message = "must be a valid email")
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
}
