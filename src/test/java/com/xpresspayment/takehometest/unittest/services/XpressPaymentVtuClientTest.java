package com.xpresspayment.takehometest.unittest.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.configs.integrations.airtime.AirtimeVtuClientProperties;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.request.AirtimeRequest;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeProductCategoryResponse;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeResponse;
import com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime;
import com.xpresspayment.takehometest.common.exceptions.HttpCallException;
import com.xpresspayment.takehometest.common.utils.HttpClientUtil;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.impl.XpressPaymentVtuClient;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime.Network.AIRTEL;
import static com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime.Network.GLO;
import static com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime.Network.MTN;
import static com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime.Network.NINE_MOBILE;
import static com.xpresspayment.takehometest.common.utils.Constants.APPLICATION_JSON;
import static com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.impl.XpressPaymentVtuClient.AIRTIME_CATEGORY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

class XpressPaymentVtuClientTest extends AbstractTest {

    @MockBean
    private XpressPaymentVtuClient xpressPaymentVtuClient;
    private AirtimeVtuClientProperties airtimeVtuClientProperties;
    private HttpClientUtil httpClientUtil;

    private  String xpressPayBillerBaseUrl;
    private HashMap<String, String> purchaseAirtimeRequestHeaders;
    private  Map<String, String> requestHeaders;
    private HashMap<String, String> getNetworkUniqueCodeRequestHeaders;
    private HashMap<String, String> getNetworkUniqueCodeRequestParams;


    @BeforeEach
    void setUp() throws HttpCallException {

        this.xpressPayBillerBaseUrl = "https://billerstest.xpresspayments.com:9603/api/v1";
        this.airtimeVtuClientProperties = AirtimeVtuClientProperties.builder()
                .privateKey("privateKey")
                .publicKey("publicKey")
                .build();
        this.httpClientUtil = mock(HttpClientUtil.class);
        xpressPaymentVtuClient = new XpressPaymentVtuClient(xpressPayBillerBaseUrl, airtimeVtuClientProperties, httpClientUtil);


        requestHeaders = new HashMap<>();
        requestHeaders.put(HttpHeaders.ACCEPT, APPLICATION_JSON);
        requestHeaders.put(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);


        getNetworkUniqueCodeRequestHeaders = new HashMap<>();
        getNetworkUniqueCodeRequestHeaders.putAll(requestHeaders);
        getNetworkUniqueCodeRequestHeaders.put(HttpHeaders.AUTHORIZATION,
                String.format("Bearer %s",
                        airtimeVtuClientProperties.getPublicKey()));
        getNetworkUniqueCodeRequestParams = new HashMap<>() {
            {
                put("categoryId", AIRTIME_CATEGORY_ID);
                put("billerId", MTN.getBillerId());
            }
        };


        purchaseAirtimeRequestHeaders = new HashMap<>();
        purchaseAirtimeRequestHeaders.putAll(requestHeaders);
        purchaseAirtimeRequestHeaders.put(HttpHeaders.AUTHORIZATION,
                String.format("Bearer %s",
                        airtimeVtuClientProperties.getPublicKey()));

        AirtimeProductCategoryResponse response = AirtimeProductCategoryResponse.builder()
                .responseCode("00")
                .responseMessage("Successful")
                .data(
                        AirtimeProductCategoryResponse.Data.builder()
                                .hasNextRecord(false)
                                .totalCount(0)
                                .productDTOList(
                                        Collections.singletonList(
                                                AirtimeProductCategoryResponse.ProductDTO.builder()
                                                        .uniqueCode("validUniqueCode")
                                                        .build()))
                                .build())
                .build();

        when(httpClientUtil.singleClassPost(
                eq(String.format("%s/products",xpressPayBillerBaseUrl)),
                anyMap(),
                anyMap(),
                eq(new Gson().toJson(
                        XpressPaymentVtuClient.PageAndSize.builder()
                                .build())),
               eq(AirtimeProductCategoryResponse.class))
        ).thenReturn(response);


        purchaseAirtimeRequestHeaders.put("PaymentHash", "dummy-payment-hash");
        purchaseAirtimeRequestHeaders.put("Channel", "api");


        AirtimeResponse airtimeResponse = AirtimeResponse.builder()
                .requestId("requestId")
                .referenceId("1234567890")
                .responseCode(0)
                .responseMessage("Successful")
                .data(
                        AirtimeResponse.Data.builder()
                                .channel("api")
                                .amount(1000)
                                .phoneNumber("08012345678")
                                .build())
                .build();

        when(httpClientUtil.singleClassPost(
                eq(String.format("%s/airtime/fulfil",xpressPayBillerBaseUrl)),
                anyMap(),
                anyMap(),
                eq(new Gson().toJson(
                        AirtimeRequest.builder()
                                .requestId("requestId")
                                .uniqueCode("validUniqueCode")
                                .details(
                                        AirtimeRequest.Details.builder()
                                                .amount(1000)
                                                .phoneNumber("08012345678")
                                                .build())
                                .build())),
                eq(AirtimeResponse.class)))
                .thenReturn(airtimeResponse);


    }



    @Test
    @DisplayName("response for unique code is request is not null")
    void testValidProviderNetworkNotNull() throws HttpCallException {
        assertNotNull(xpressPaymentVtuClient.getNetworkUniqueCode(MTN), "unique code should not be null");
        assertNotNull(xpressPaymentVtuClient.getNetworkUniqueCode(AIRTEL), "unique code should not be null");
        assertNotNull(xpressPaymentVtuClient.getNetworkUniqueCode(GLO), "unique code should not be null");
        assertNotNull(xpressPaymentVtuClient.getNetworkUniqueCode(NINE_MOBILE), "unique code should not be null");
    }

    @Test
    @DisplayName("valid  unique code is returned for network purchase")
    void testValidProviderNetworkValidUniqueCode() throws HttpCallException {
        assertEquals("validUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(MTN), "unique code should be valid");
        assertEquals("validUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(AIRTEL), "unique code should be valid");
        assertEquals("validUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(GLO), "unique code should be valid");
        assertEquals("validUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(NINE_MOBILE), "unique code should be valid");
    }


    @Test
    @DisplayName("invalid code unique code is not returned for network purchase")
    void testValidProviderNetworkInvalidUniqueCode() throws HttpCallException {
        assertNotEquals("InvalidUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(MTN), "unique code should be invalid");
        assertNotEquals("InvalidUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(AIRTEL), "unique code should be invalid");
        assertNotEquals("InvalidUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(GLO), "unique code should be invalid");
        assertNotEquals("InvalidUniqueCode", xpressPaymentVtuClient.getNetworkUniqueCode(NINE_MOBILE), "unique code should be invalid");
    }


    @Test
    @DisplayName("valid airtime purchase with correct requestId is not null")
    void testValidAirtimePurchaseNotNull() {
        assertNotNull(xpressPaymentVtuClient.purchaseAirtime(
                PurchaseAirtime.builder()
                        .amount(1000)
                        .phoneNumber("08012345678")
                        .network(MTN)
                        .build(), "requestId").getResponseMessage()
                , "airtime purchase should not be null");
    }


    @Test
    @DisplayName("valid airtime purchas with correct requestId is successful")
    void testValidAirtimePurchase() {
        assertEquals("Successful",
                xpressPaymentVtuClient.purchaseAirtime(PurchaseAirtime.builder()
                        .amount(1000)
                        .phoneNumber("08012345678")
                        .network(MTN)
                        .build(), "requestId").getResponseMessage()
                , "airtime purchase should be successful");
    }

    @Test
    @DisplayName("valid airtime purchase with incorrect request id is null")
    void testInvValidAirtimePurchaseNull() {
        assertNull( xpressPaymentVtuClient.purchaseAirtime(PurchaseAirtime.builder()
                        .amount(1000)
                        .phoneNumber("08012345678")
                        .network(MTN)
                        .build(), "wrongRequestId")
                , "airtime purchase should be successful");
    }
}