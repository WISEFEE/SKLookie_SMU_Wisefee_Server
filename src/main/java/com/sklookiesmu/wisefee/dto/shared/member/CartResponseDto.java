package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CartResponseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "장바구니 조회 DTO")
    public static class CartProductResponseDto{

        @ApiModelProperty(value = "카페 아이디")
        private Long cafeId;
        @ApiModelProperty(value = "카페 이름")
        private String cafeName;
        @ApiModelProperty(value = "상품 아이디")
        private Long productId;
        @ApiModelProperty(value = "상품 이름")
        private String productName;
        @ApiModelProperty(value = "상품 정보")
        private String productInfo;
        @ApiModelProperty(value = "상품 가격")
        private Integer productPrice;
        @ApiModelProperty(value = "상품 개수")
        private Long productQuantity;
        @ApiModelProperty(value = "선택 옵션 리스트")
        private List<ProductOptChoiceResponseDTO> productOptChoices;
        @ApiModelProperty(value = "결제 여부")
        private String cartStatus;
        @ApiModelProperty(value = "생성일")
        private LocalDateTime createdAt;
        @ApiModelProperty(value = "수정일")
        private LocalDateTime updatedAt;

        @Override
        public String toString() {
            return "CartProductResponseDto{" +
                    "cafeId=" + cafeId +
                    ", cafeName='" + cafeName + '\'' +
                    ", productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", productInfo='" + productInfo + '\'' +
                    ", productPrice=" + productPrice +
                    ", productQuantity=" + productQuantity +
                    ", productOptChoices=" + productOptChoices +
                    ", cartStatus='" + cartStatus + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    '}';
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("장바구니 선택옵션 조회 API")
    public static class ProductOptChoiceResponseDTO {
        @ApiModelProperty(value = "상품 옵션 이름")
        private String productOptName;
        @ApiModelProperty(value = "선택 옵션 아이디")
        private Long productOptChoiceId;
        @ApiModelProperty(value = "선택 옵션 이름")
        private String productOptChoiceName;
        @ApiModelProperty(value = "선택 옵션 가격")
        private Integer productOptChoicePrice;

        @Override
        public String toString() {
            return "ProductOptChoiceResponseDTO{" +
                    "productOptName='" + productOptName + '\'' +
                    ", productOptChoiceId=" + productOptChoiceId +
                    ", productOptChoiceName='" + productOptChoiceName + '\'' +
                    ", productOptChoicePrice=" + productOptChoicePrice +
                    '}';
        }
    }


}
