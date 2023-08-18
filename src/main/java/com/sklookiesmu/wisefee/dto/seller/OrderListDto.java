package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장에 들어온 주문리스트 정보 DTO")
public class OrderListDto {

    @ApiModelProperty(value = "주문 ID")
    private Long orderId;

    @ApiModelProperty(value = "주문 일시")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "주문 유효 시간")
    private LocalDateTime productReceiveTime;

    @ApiModelProperty(value = "주문 상태")
    private ProductStatus productStatus;


    public static OrderListDto fromOrder(Order order) {

        return new OrderListDto(
                order.getOrderId(),
                order.getCreatedAt(),
                order.getProductReceiveTime(),
                order.getProductStatus()
        );
    }
}
