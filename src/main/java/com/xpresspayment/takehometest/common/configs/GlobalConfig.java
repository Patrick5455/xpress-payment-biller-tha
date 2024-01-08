package com.xpresspayment.takehometest.common.configs;

import lombok.Builder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public CloseableHttpClient httpClient () {
        return HttpClients.createDefault();
    };
}
