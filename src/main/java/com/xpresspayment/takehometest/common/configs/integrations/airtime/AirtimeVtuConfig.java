package com.xpresspayment.takehometest.common.configs.integrations.airtime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class AirtimeVtuConfig {

    @Profile({"!test"})
    @ConfigurationProperties(prefix = "vtu")
    @Bean
    public AirtimeVtuClientProperties airtimeVtuClientProperties(){
        return AirtimeVtuClientProperties.builder()
                .build();
    }

    @Value("${xpress.pay.biller.base.url}")
    private String xpressPayBillerBaseUrl;
    @Bean
    @Profile({"!test"})
    public String xpressPayBillerBaseUrl() {
        return xpressPayBillerBaseUrl;
    }

}
