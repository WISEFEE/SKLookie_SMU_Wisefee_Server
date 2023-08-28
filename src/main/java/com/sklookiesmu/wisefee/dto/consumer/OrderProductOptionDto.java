package com.sklookiesmu.wisefee.dto.consumer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderProductOptionDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderProductOptionRequestDto{

        @ApiModelProperty(value = "주문 상품 옵션 PK", required = true)
        @NotBlank(message = "주문 상품 옵션 PK는 필수 입력값입니다.")
        private Long orderProductOptionId;

        @ApiModelProperty(value = "주문상품 옵션 선택", required = true)
        private List<OrderProductOptionChoiceRequestDto> productOptionChoices;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "OrderProduct 옵션 추가 DTO")
    public static class OrderProductOptionChoiceRequestDto{
        @ApiModelProperty(value = "선택 상품옵션 아이디", example = "1")
        @NotNull(message = "선택 상품옵션 아이디는 필수 입력 값입니다.")
        private Long orderProductOptionChoiceId;
    }
}
