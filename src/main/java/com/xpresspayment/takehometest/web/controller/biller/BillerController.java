package com.xpresspayment.takehometest.web.controller.biller;


import java.net.URI;
import javax.validation.Valid;

import com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response.AirtimeResponse;
import com.xpresspayment.takehometest.common.dto.web.request.PurchaseAirtime;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.services.thirdpartyintegration.airtime.i.AirtimeVtuClient;
import com.xpresspayment.takehometest.web.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/biller")
@AllArgsConstructor
@Slf4j
public class BillerController extends BaseController {

    private final AirtimeVtuClient airtimeVtuClient;

    @Operation(
            summary = "Purchase Airtime VTC",
            description = "This endpoint allows an authenticated user to purchase airtime vtu"
    )

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "not found")
            }
    )
    @PostMapping(value = "/airtime", produces = {})
    public ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> purchaseAirtime(@Valid  @RequestBody PurchaseAirtime request) {
        ResponseEntity<com.xpresspayment.takehometest.common.dto.web.response.ApiResponse> responseEntity;
        try {
            AirtimeResponse response = airtimeVtuClient.purchaseAirtime(request, GlobalUtils.generateUuid());
            responseEntity = ResponseEntity.created(URI.create("")).
                    body(toApiResponse(response, "user successfully purchased airtime",
                            HttpStatusCode.STATUS_200.getCode(), true));
        }
        catch (AppException e){
            log.error("an exception occurred while purchasing airtime", e);
            responseEntity = ResponseEntity.badRequest().body(
                    toErrorResponse(e.getMessage(), HttpStatusCode.STATUS_500.getCode())
            );
        }
        return responseEntity;
    }
}
