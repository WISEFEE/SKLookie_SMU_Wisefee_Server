package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


public class CartRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "CartProduct 추가 DTO")
    public static class CartProductRequestDto {
        @ApiModelProperty(value = "카페 아이디", example = "1")
        @NotNull(message = "카페 아이디는 필수 입력 값입니다.")
        private Long cafeId;
        @ApiModelProperty(value = "상품 아이디", example = "1")
        @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
        private Long productId;
        @ApiModelProperty(value = "상품 개수", example = "1")
        @Min(value = 1, message = "상품 개수는 1 이상이어야 합니다.")
        @NotNull(message = "상품 개수는 필수 입력 값입니다.")
        private Long productQuantity;
        @ApiModelProperty(value = "선택상품 옵션 리스트", example = "[1,2,3]")
        private List<ProductOptionChoiceAsCartRequestDto> productOptChoices;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "CartProduct 옵션 추가 DTO")
    public static class ProductOptionChoiceAsCartRequestDto {
        @ApiModelProperty(value = "선택 상품옵션 아이디", example = "1")
        @NotNull(message = "선택 상품옵션 아이디는 필수 입력 값입니다.")
        private Long optionChoiceId;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "CartPorduct 옵션 업데이트 DTO")
    public static class CartProductUpdateRequestDTO {
        @ApiModelProperty(value = "추가할 상품 개수", example = "1")
        @NotNull(message = "변경할 상품 개수는 필수 입력 값입니다.")
        private Long addProductQuantity;
    }

}

