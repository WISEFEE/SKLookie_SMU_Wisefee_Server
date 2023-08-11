//package com.sklookiesmu.wisefee.dto.seller;
//
//import com.sklookiesmu.wisefee.domain.OrderProduct;
//import com.sklookiesmu.wisefee.domain.Product;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ApiModel(description = "주문 상품 정보 DTO")
//public class OrderProductDto {
//
//    @ApiModelProperty(value = "상품 ID")
//    private Long productId;
//
//    @ApiModelProperty(value = "상품 이름")
//    private String productName;
//
//    @ApiModelProperty(value = "상품 수량")
//    private Integer quantity;
//
//    public static OrderProductDto fromOrderProduct(OrderProduct orderProduct) {
//        Product product = orderProduct.getProduct();
//
//        return new OrderProductDto(
//                product.getProductId(),
//                product.getProductName(),
//                orderProduct.getQuantity()
//        );
//    }
//}
