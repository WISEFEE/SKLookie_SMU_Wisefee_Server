package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.OrderOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CafeOrderDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CafeOrderResponseDto{
        private Long orderOptionId;
        private String orderOptionName;
        private int orderOptionPrice;

        public static CafeOrderResponseDto from(OrderOption orderOption){
            return new CafeOrderResponseDto(
                    orderOption.getOrderOptionId(),
                    orderOption.getOrderOptionName(),
                    orderOption.getOrderOptionPrice());
        }
    }
}
