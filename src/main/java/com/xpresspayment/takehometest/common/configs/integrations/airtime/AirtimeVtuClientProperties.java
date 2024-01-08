package com.xpresspayment.takehometest.common.configs.integrations.airtime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirtimeVtuClientProperties {

    private String privateKey;
    private String publicKey;
}
