
package com.xpresspayment.takehometest.common.dto.web.response;

import lombok.Data;


@Data
public class HttpClientResponse {
    private int code;
    private String status;
    private String message;
    private String payload;
}
