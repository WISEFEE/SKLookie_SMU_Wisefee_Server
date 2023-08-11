//package com.sklookiesmu.wisefee.dto.seller;
//
//import com.sklookiesmu.wisefee.common.constant.ProductStatus;
//import com.sklookiesmu.wisefee.domain.Order;
//import com.sklookiesmu.wisefee.domain.TumblrStatus;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiModel(description = "주문 정보 DTO")
//public class OrderDto {
//
//    @ApiModelProperty(value = "주문 ID")
//    private Long orderId;
//
//    @ApiModelProperty(value = "상품 목록")
//    private List<OrderProductDto> orderProducts;
//
//    public static OrderDto fromOrder(Order order) {
//        List<OrderProductDto> orderProductDtos = order.getOrderProducts().stream()
//                .map(OrderProductDto::fromOrderProduct)
//                .collect(Collectors.toList());
//
//        return new OrderDto(
//                order.getOrderId(),
//                order.getProductStatus(),
//                order.getCreatedAt(),
//                orderProductDtos
//        );
//    }
//}
