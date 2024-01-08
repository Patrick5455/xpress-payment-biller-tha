package com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.common.configs.integrations.airtime.AirtimeVtuClientProperties;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.request.AirtimeRequest;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeResponse;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeProductCategoryResponse;
import com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.exceptions.HttpCallException;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.common.utils.HttpClientUtil;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.i.AirtimeVtuClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static com.xpresspayment.takehometest.common.utils.Constants.APPLICATION_JSON;
import static com.xpresspayment.takehometest.common.utils.GlobalUtils.calculateHMAC512;

@Slf4j
@Component
@AllArgsConstructor
public class XpressPaymentVtuClient implements AirtimeVtuClient {

    public final static String AIRTIME_CATEGORY_ID = "1";
    private String xpressPayBillerBaseUrl;
    private final AirtimeVtuClientProperties airtimeVtuClientProperties;
    private final HttpClientUtil httpClientUtil;
    private final Map<String, String> requestHeaders = new HashMap<>();

    @PostConstruct
    public void setRequestHeaders() {
        requestHeaders.put(HttpHeaders.ACCEPT, APPLICATION_JSON);
        requestHeaders.put(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
    }

    @Override
    public AirtimeResponse purchaseAirtime(PurchaseAirtime request, String requestId) {
        try {

            String createVirtualAccountEndpoint = String.format("%s/airtime/fulfil", xpressPayBillerBaseUrl);
            String requestBody = new Gson().toJson(buildAirtimeRequest(request, requestId));
            log.info("buildAirtimeRequest requestBody: {}", requestBody);
            requestHeaders.put(HttpHeaders.AUTHORIZATION,
                    String.format("Bearer %s",
                            airtimeVtuClientProperties.getPublicKey()));
            requestHeaders.put("PaymentHash", calculateHMAC512(requestBody,
                    airtimeVtuClientProperties.getPrivateKey()));
            requestHeaders.put("Channel", "api");

            AirtimeResponse response = httpClientUtil.singleClassPost(
                    createVirtualAccountEndpoint,
                    requestHeaders,
                    new HashMap<>(),
                    requestBody,
                    AirtimeResponse.class
            );

            log.info("airtime vtu created successfully");
            return response;

        } catch (HttpCallException e) {
            log.error("something went wrong while making request to purchase airtime", e);
            throw new AppException("airtime cannot be purchased at this time, please try again later");
        }
    }


    public AirtimeRequest buildAirtimeRequest(PurchaseAirtime purchaseAirtime, String requestId) throws HttpCallException {

        return AirtimeRequest.builder()
                .requestId(requestId)
                .uniqueCode(getNetworkUniqueCode(purchaseAirtime.getNetwork()))
                .details(
                        AirtimeRequest.Details.builder()
                                .amount(purchaseAirtime.getAmount())
                                .phoneNumber(purchaseAirtime.getPhoneNumber())
                                .build()
                )
                .build();
    }

    public String getNetworkUniqueCode(PurchaseAirtime.Network network) throws HttpCallException {

        String createVirtualAccountEndpoint = String.format("%s/products", xpressPayBillerBaseUrl);
        String requestBody = new Gson().toJson(PageAndSize.builder().build());
        HashMap<String, String> params = new HashMap<>() {
            {
                put("categoryId", AIRTIME_CATEGORY_ID);
                put("billerId", network.getBillerId());
            }
        };

        requestHeaders.put(HttpHeaders.AUTHORIZATION,
                String.format("Bearer %s",
                        airtimeVtuClientProperties.getPublicKey()));

        log.info("requestHeaders: {}", requestHeaders);
        log.info("params: {}", params);
        log.info("requestBody: {}", requestBody);
        AirtimeProductCategoryResponse response = httpClientUtil.singleClassPost(
                createVirtualAccountEndpoint,
                requestHeaders, params,
                requestBody,
                AirtimeProductCategoryResponse.class
        );
        log.info("response from xpress payment: {}", response);
        List<AirtimeProductCategoryResponse.ProductDTO> productDTOList = response.getData().getProductDTOList();
        if (!productDTOList.isEmpty()) {
            log.info("network unique code retrieved successfully");
            return productDTOList.get(0).getUniqueCode();
    }
        throw new AppException("airtime purchase service is not available at the moment");
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageAndSize {
        @Builder.Default
        private int page = 0;
        @Builder.Default
        private int size = 1;
    }
}

