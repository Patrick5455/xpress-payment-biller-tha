package io.revnorth.webdtos.response.auth.account;

import io.revnorth.enumconstant.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private UserType user_type;
    private OnBoardingDetails onBoarding_details;
    private String created_at;
}
