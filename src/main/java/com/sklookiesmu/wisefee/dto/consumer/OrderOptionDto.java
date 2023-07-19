package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.OrderOption;
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
        private Long orderOptionId;
        private String orderOptionName;
        private int orderOptionPrice;
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
