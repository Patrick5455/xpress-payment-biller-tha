package com.xpresspayment.takehometest.common.dto.web.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAirtime {

    private String phoneNumber;
    private Integer amount;
    private Network network;


    @Getter
    public enum Network {
        GLO("21"),  MTN("20"), AIRTEL("19"), NINE_MOBILE("18");

        private final String billerId;

        Network(String billerId){
            this.billerId = billerId;
        }
    }
}
