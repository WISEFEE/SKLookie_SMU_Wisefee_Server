package com.sklookiesmu.wisefee.dto.consumer;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersInfoDto {
    private List<OrderInfoDto.OrderInfoResponseDto> orderInformation = new ArrayList<>();

    public void addOrderInfoResponseDto(OrderInfoDto.OrderInfoResponseDto orderInfoDto) {
        orderInformation.add(orderInfoDto);
    }
}
