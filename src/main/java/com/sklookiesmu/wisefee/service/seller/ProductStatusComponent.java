package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStatusComponent {

    private final OrderService orderService;

    public void acceptOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.ACCEPTED);
    }

    public void rejectOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.REJECTED);
    }

    public void preparingOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.PREPARING);
    }

    public void completeOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.COMPLETED);
    }

}