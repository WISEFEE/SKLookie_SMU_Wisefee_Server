package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CafeProductDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeProductResponseDto{

        @ApiModelProperty(value = "제품명", required = true)
        private String productName;

        @ApiModelProperty(value = "제품가격", required = true)
        private int productPrice;

        @ApiModelProperty(value = "제품정보", required = true)
        private String productInfo;

        public static CafeProductResponseDto from(Product product){
            return new CafeProductResponseDto(
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getProductInfo());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CafeProductListResponseDto{
        private List<CafeProductDto.CafeProductResponseDto> products;

        public static CafeProductListResponseDto of(List<Product> productList){
            List<CafeProductDto.CafeProductResponseDto> productResponses = productList
                    .stream()
                    .map(CafeProductDto.CafeProductResponseDto::from)
                    .collect(Collectors.toList());

            return new CafeProductListResponseDto(productResponses);
        }
    }
}
