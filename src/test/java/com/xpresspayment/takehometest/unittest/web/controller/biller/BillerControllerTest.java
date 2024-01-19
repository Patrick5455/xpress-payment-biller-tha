package com.xpresspayment.takehometest.unittest.web.controller.biller;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeResponse;
import com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.i.AirtimeVtuClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class BillerControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirtimeVtuClient airtimeVtuClient;

    @Test
    void testPurchaseAirtime() throws Exception {
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

        Mockito.when(airtimeVtuClient.purchaseAirtime(Mockito.any(PurchaseAirtime.class), Mockito.anyString()))
                .thenReturn(airtimeResponse);

        PurchaseAirtime purchaseAirtime = PurchaseAirtime.builder()
                .amount(10000)
                .network(PurchaseAirtime.Network.MTN)
                .phoneNumber("08104708102")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/biller/airtime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(purchaseAirtime)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatusCode.STATUS_200.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user successfully purchased airtime"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    private String asJsonString(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
