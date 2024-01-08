package com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeResponse {

    private String requestId;
    private String referenceId;
    private Integer responseCode;
    private String responseMessage;
    private Object data;



}
