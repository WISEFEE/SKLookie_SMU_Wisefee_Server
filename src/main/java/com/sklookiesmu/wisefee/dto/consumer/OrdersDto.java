package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDto {
    private List<OrderDto.OrderResponseDto> orders = new ArrayList<>();

    public OrdersDto(Order order) {
    }

    public void addOrderResponseDto(OrderDto.OrderResponseDto orderDto) {
        orders.add(orderDto);
    }
}
