package com.xpresspayment.takehometest.common.configs.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Data @With @NoArgsConstructor @AllArgsConstructor
@Builder
public class SmsProperties {

    private String password;
    private String from;
    private String accountSid;
    private String authToken;
}
