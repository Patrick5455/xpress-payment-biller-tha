

package com.xpresspayment.takehometest;

import javax.sql.DataSource;

import com.xpresspayment.takehometest.common.configs.integrations.airtime.AirtimeVtuClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.codemonkey.simplejavamail.Mailer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@TestConfiguration
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegratedTestConfigs {

    @Bean(name = "testAirtimeVtuClientProperties")
    @ConfigurationProperties(prefix = "test.vtu")
    @Profile("test")
    public AirtimeVtuClientProperties airtimeVtuClientProperties () {
        return AirtimeVtuClientProperties.builder().build();
    }


    @Value("${xpress.pay.biller.base.url}")
    private String xpressPayBillerBaseUrl;
    @Bean(name = "testXpressPayBillerBaseUrl")
    @Profile("test")
    public String xpressPayBillerBaseUrl () {
        return xpressPayBillerBaseUrl;
    }

}
