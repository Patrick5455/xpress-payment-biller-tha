package com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.i;

import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeResponse;
import com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import org.springframework.stereotype.Component;

@Component
public interface AirtimeVtuClient {

    public AirtimeResponse purchaseAirtime(PurchaseAirtime request, String requestId) throws AppException;

}
