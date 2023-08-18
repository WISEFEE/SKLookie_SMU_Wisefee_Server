package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStatusComponent {

    private final OrderService orderService;

    public void acceptOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.ACCEPT);
    }

    public void rejectOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.REJECT);
    }

    public void preparingOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.ALLSET);
    }

    public void completeOrder(Long orderId) {
        orderService.updateOrderStatus(orderId, ProductStatus.RECEIVE);
    }

}