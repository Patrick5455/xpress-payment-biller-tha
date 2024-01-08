

package com.xpresspayment.takehometest.common.configs.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data @With @NoArgsConstructor @AllArgsConstructor
@Builder
public class CacheProperties {

    @Builder.Default
    private String host="localhost";
    private String password;
    private String port;
    private String url;
}
