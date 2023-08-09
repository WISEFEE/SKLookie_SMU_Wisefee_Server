package com.sklookiesmu.wisefee.dto.shared.member;

import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class CartDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "CartProduct 추가 DTO")
    public static class CartProductRequestDto {
        @ApiModelProperty(value = "장바구니 아이디", example = "1")
        private Long cartId;
        @ApiModelProperty(value = "카페 아이디", example = "1")
        private Long cafeId;
        @ApiModelProperty(value = "상품 아이디", example = "1")
        private Long productId;
        @ApiModelProperty(value = "선택상품 옵션 리스트", example = "[1,2,3]")
        private List<ProductOptionChoiceAsCartRequestDto> productOptChoices;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "CartProduct 옵션 추가 DTO")
    public static class ProductOptionChoiceAsCartRequestDto{
        @ApiModelProperty(value = "선택 상품옵션 아이디", example = "1")
        private Long optionChoiceId;
    }


}

