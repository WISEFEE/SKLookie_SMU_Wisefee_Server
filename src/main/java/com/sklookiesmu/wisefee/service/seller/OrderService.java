package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Order;

import java.util.List;

public interface OrderService {

    /**
     * [주문 상태 업데이트]
     * 주문의 상태를 새로운 상태로 업데이트합니다.
     *
     * @param orderId 주문 ID
     * @param newStatus 새로운 주문 상태
     * @throws IllegalArgumentException 존재하지 않는 주문일 경우 예외를 던집니다.
     */
    void updateOrderStatus(Long orderId, ProductStatus newStatus);

    public Order getOrderById(Long orderId);

    public List<Order> getOrdersByCafeId(Long cafeId);
}
