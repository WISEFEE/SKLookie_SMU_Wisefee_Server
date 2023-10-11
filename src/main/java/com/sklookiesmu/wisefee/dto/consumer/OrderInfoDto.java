package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class OrderInfoDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderInfoResponseDto{

        @ApiModelProperty(value = "카페 아이디", required = true)
        private Long cafeId;

        @ApiModelProperty(value = "주문 상태", required = true)
        private ProductStatus productStatus;

        @ApiModelProperty(value = "주문 생성시간", required = true)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "현재 정기구독권 PK", required = true)
        private Long subscribeId;

        @ApiModelProperty(value = "주문 상품", required = true)
        private List<OrderDto.OrderProductRequestDto> orderProduct;

        @ApiModelProperty(value = "주문 옵션", required = true)
        private List<OrderDto.OrdOrderOptionRequestDto> orderOption;

        @ApiModelProperty(value = "매장 주소", required = true)
        private Long addressId;

        @ApiModelProperty(value = "결재 금액", required = true)
        private Long paymentPrice;

        public static OrderInfoDto.OrderInfoResponseDto orderToDto(Order order){
            return OrderInfoDto.OrderInfoResponseDto.builder()
                    .productStatus(order.getProductStatus())
                    .createdAt(order.getCreatedAt())
                    .subscribeId(order.getSubscribe().getSubId())
                    .addressId(order.getSubscribe().getCafe().getAddress().getAddrId())
                    .paymentPrice(order.getPayment().getPaymentPrice())
                    .cafeId(order.getSubscribe().getCafe().getCafeId())

                    .orderProduct(order.getOrderProducts().stream()
                            .map(orderProd -> OrderDto.OrderProductRequestDto.builder()
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
                            .map(orderOpt-> OrderDto.OrdOrderOptionRequestDto.builder()
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
