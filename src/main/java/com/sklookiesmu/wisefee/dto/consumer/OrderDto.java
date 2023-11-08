package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.common.constant.PaymentMethod;
import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderRequestDto{

        @ApiModelProperty(value = "구독권 ID", required = true)
        @NotNull(message = "구독권 ID는 필수 입력값입니다.")
        private Long subscribeId;

        @ApiModelProperty(value = "결제수단", required = true)
        @NotNull(message = "결제수단은 필수 입력값입니다.")
        private PaymentMethod paymentMethod;

        @ApiModelProperty(value = "주문 상품", required = true)
        @NotNull(message = "주문 상품은 필수 입력값입니다.")
        private List<OrderProductRequestDto> orderProduct;

        @ApiModelProperty(value = "주문 옵션", required = true)
        private List<OrdOrderOptionRequestDto> orderOption;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderResponseDto{

        @ApiModelProperty(value = "주문 상태", required = true, example = "Y")
        private ProductStatus productStatus;

        @ApiModelProperty(value = "주문 생성시간", required = true)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "현재 정기구독권 PK", required = true)
        private Long subscribeId;

        @ApiModelProperty(value = "주문 상품", required = true)
        private List<OrderProductRequestDto> orderProduct;

        @ApiModelProperty(value = "주문 옵션", required = true)
        private List<OrdOrderOptionRequestDto> orderOption;

        public static OrderResponseDto orderToDto(Order order){
            return OrderResponseDto.builder()
                    .productStatus(order.getProductStatus())
                    .createdAt(order.getCreatedAt())
                    .subscribeId(order.getSubscribe().getSubId())

                    .orderProduct(order.getOrderProducts().stream()
                            .map(orderProd -> OrderProductRequestDto.builder()
                                    .productId(orderProd.getProduct().getProductId())

                                    .productOption(orderProd.getOrderProductOptions().stream()
                                            .map(prodOpt -> OrderProductOptionDto.OrderProductOptionRequestDto.builder()
                                                    .orderProductOptionId(prodOpt.getProductOption().getProductOptionId())

                                                    .productOptionChoices(prodOpt.getOrderProductOptChoice().stream()
                                                            .map(prodOptChoice -> OrderProductOptionDto.OrderProductOptionChoiceRequestDto.builder()
                                                                    .orderProductOptionChoiceId(prodOptChoice.getProductOptChoice().getProductOptionChoiceId())
                                                                    .build())
                                                            .collect(Collectors.toList()))
                                                    .build())
                                            .collect(Collectors.toList()))

                                    .build())
                            .collect(Collectors.toList()))

                    .orderOption(order.getOrdOrderOptions().stream()
                            .map(orderOpt-> OrdOrderOptionRequestDto.builder()
                                    .orderOptionId(orderOpt.getOrderOption().getOrderOptionId())
                                    .build())
                            .collect(Collectors.toList())).build();
        }
    }
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderProductRequestDto{

        @ApiModelProperty(value = "제품 PK", required = true)
        @NotBlank(message = "제품 PK는 필수 입력값입니다.")
        private Long productId;

        @ApiModelProperty(value = "제품 옵션", required = true)
        //@ApiModelProperty(value = "제품 옵션")
        private List<OrderProductOptionDto.OrderProductOptionRequestDto> productOption;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrdOrderOptionRequestDto{

        @ApiModelProperty(value = "주문 옵션 PK", required = true)
        private Long orderOptionId;
    }
}
