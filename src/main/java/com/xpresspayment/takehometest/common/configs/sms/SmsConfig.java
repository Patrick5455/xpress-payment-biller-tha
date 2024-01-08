package com.xpresspayment.takehometest.commons.configs.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SmsConfig {

    @Profile("!unittest")
    @ConfigurationProperties(prefix = "sms")
    @Bean
    public SmsProperties smsProperties (){
        return SmsProperties.builder().build();
    }



}
