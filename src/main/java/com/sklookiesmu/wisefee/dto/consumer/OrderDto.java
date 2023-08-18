package com.sklookiesmu.wisefee.dto.consumer;

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

        @ApiModelProperty(value = "주문 상품", required = true)
        @NotNull(message = "주문 상품은 필수 입력값입니다.")
        private List<OrderProductRequestDto> orderProduct;

        @ApiModelProperty(value = "주문 옵션", required = true)
        private List<OrderOptionDto.OrderOptionRequestDto> orderOption;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderResponseDto{

        @ApiModelProperty(value = "텀블러 반납 상태", required = true, example = "Y")
        private TumblrStatus tumblrStatus;

        @ApiModelProperty(value = "주문 상태", required = true, example = "Y")
        private ProductStatus productStatus;

        @ApiModelProperty(value = "주문 생성시간", required = true)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "현재 정기구독권 PK", required = true)
        private Long subscribeId;

        @ApiModelProperty(value = "주문 상품", required = true)
        private List<OrderProductRequestDto> orderProduct;

        @ApiModelProperty(value = "주문 옵션", required = true)
        private List<OrderOptionDto.OrderOptionRequestDto> orderOption;

        public static OrderResponseDto orderToDto(Order order){
            return OrderResponseDto.builder()
                    .tumblrStatus(order.getTumblrStatus())
                    .productStatus(order.getProductStatus())
                    .createdAt(order.getCreatedAt())
                    .subscribeId(order.getSubscribe().getSubId())

                    .orderProduct(order.getOrderProducts().stream()
                            .map(orderProd -> OrderProductRequestDto.builder()
                                    .productId(orderProd.getOrderProductId())

                                    .productOption(orderProd.getProduct().getProductOptions().stream()
                                            .map(prodOpt -> ProductDto.ProductOptionRequestDto.builder()
                                                    .productOptionId(prodOpt.getProductOptionId())

                                                    .productOptionChoice(prodOpt.getProductOptChoices().stream()
                                                            .map(prodOptChoice -> ProductDto.ProductOptionChoiceRequestDto.builder()
                                                                    .optionChoiceId(prodOptChoice.getProductOptionChoiceId())
                                                                    .build())
                                                            .collect(Collectors.toList()))
                                                    .build())
                                            .collect(Collectors.toList()))

                                    .build())
                            .collect(Collectors.toList()))

                    .orderOption(order.getOrderOptions().stream()
                            .map(orderOpt-> OrderOptionDto.OrderOptionRequestDto.builder()
                                    .orderOptionId(orderOpt.getOrderOptionId())
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
        private List<ProductDto.ProductOptionRequestDto> productOption;
    }
}
