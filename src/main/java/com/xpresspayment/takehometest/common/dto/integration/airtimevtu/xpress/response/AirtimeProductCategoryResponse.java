package com.xpresspayment.takehometest.common.dto.integration.airtimevtu.xpress.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirtimeProductCategoryResponse {

    public String responseCode;
    public String responseMessage;
    public Data data;


    @lombok.Data
    @Builder
    public static class Data{
        private boolean hasNextRecord;
        private int totalCount;
        private List<ProductDTO> productDTOList;
    }

    @lombok.Data
    @Builder
    public static class ProductDTO {
        private String name;
        private String uniqueCode;
        private boolean lookUp;
        private boolean fixedAmount;
        private int amount;
        private int minimumAmount;
        private int maximumAmount;
        private String imageUrl;
        private String billerName;
        private CategoryDTO categoryDTO;
    }


    @lombok.Data
    @Builder
    public static class CategoryDTO{
        private int id;
        private String name;
    }



}
