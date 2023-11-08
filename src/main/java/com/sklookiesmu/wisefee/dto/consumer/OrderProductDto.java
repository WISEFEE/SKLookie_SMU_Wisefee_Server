package com.sklookiesmu.wisefee.dto.consumer;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderProductDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductResponseDto{

        @ApiModelProperty(value = "주문 상품 PK", required = true)
        private Long orderProductId;

        @ApiModelProperty(value = "주문한 상품 옵션 정보", required = true)
        private List<ProductDto.ProductOptionResponseDto> productOption;
    }
}
