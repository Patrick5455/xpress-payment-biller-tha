

package com.xpresspayment.takehometest.common.configs.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data @With @NoArgsConstructor @AllArgsConstructor
public class DBProperties {

    private String name;
    private String username;
    private String password;
    private String host;
    private String driver;
    private long port;

}
