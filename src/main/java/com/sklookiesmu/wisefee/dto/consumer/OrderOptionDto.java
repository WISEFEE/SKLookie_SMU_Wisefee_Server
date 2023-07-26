package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.OrderOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class OrderOptionDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderOptionResponseDto{

        @ApiModelProperty(value = "주문 옵션 PK", required = true)
        private Long orderOptionId;

        @ApiModelProperty(value = "주문 옵션 정보", required = true)
        private String orderOptionName;

        @ApiModelProperty(value = "주문 옵션 가격", required = true)
        private int orderOptionPrice;

        @ApiModelProperty(value = "주문 생성일", required = true)
        private LocalDateTime createdAt;

        public static OrderOptionResponseDto from(OrderOption orderOption){
            return new OrderOptionResponseDto(
                    orderOption.getOrderOptionId(),
                    orderOption.getOrderOptionName(),
                    orderOption.getOrderOptionPrice(),
                    orderOption.getCreatedAt());
        }
    }
}
