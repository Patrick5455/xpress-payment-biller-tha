package com.xpresspayment.takehometest.commons.dto.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private String message;
    private int code;
    private boolean success;
}
