package com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.request;

import javax.validation.constraints.NotBlank;

import com.xpresspayment.takehometest.common.annotations.constraints.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeRequest {

    @NotBlank
    private String requestId;
    @NotBlank
    private String uniqueCode;
    private Details details;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Details {

        @NotBlank
        @ValidPhoneNumber
        private String phoneNumber;
        @NotBlank
        private Integer amount;
    }

}
