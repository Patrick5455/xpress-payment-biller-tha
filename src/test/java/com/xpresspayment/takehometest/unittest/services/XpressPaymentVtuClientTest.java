package com.xpresspayment.takehometest.integrationtest;

import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.configs.integrations.airtime.AirtimeVtuClientProperties;
import com.xpresspayment.takehometest.common.utils.HttpClientUtil;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.impl.XpressPaymentVtuClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class XpressPaymentVtuClientTest extends AbstractTest {

    private XpressPaymentVtuClient xpressPaymentVtuClient
    private AirtimeVtuClientProperties airtimeVtuClientProperties;
    private String xpressPayBillerBaseUrl = "https://sandbox.xpresspayment.com/api/v1/biller";
    private HttpClientUtil httpClientUtil;

    @BeforeEach
    void setUp() {
        airtimeVtuClientProperties = AirtimeVtuClientProperties.builder()
                .privateKey("privateKey")
                .publicKey("publicKey")
                .build();
        httpClientUtil = mock(HttpClientUtil.class);
        xpressPaymentVtuClient = new XpressPaymentVtuClient(xpressPayBillerBaseUrl, airtimeVtuClientProperties);
    }

    
    @Test
    void purchaseAirtime() {
    }
}