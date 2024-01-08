

package com.xpresspayment.takehometest;

import javax.sql.DataSource;

import com.xpresspayment.takehometest.common.configs.integrations.airtime.AirtimeVtuClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.codemonkey.simplejavamail.Mailer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;


@TestConfiguration
@Slf4j
public class IntegratedTestConfigs {

    @Bean(name = "testAirtimeVtuClientProperties")
    @ConfigurationProperties(prefix = "test.vtu")
    public AirtimeVtuClientProperties airtimeVtuClientProperties () {
        return AirtimeVtuClientProperties.builder().build();
    }

}
