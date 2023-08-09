package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.service.seller.OrderStatusComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderStatusComponent orderStatusComponent;

    @PostMapping("/{orderId}/accept")
    public void acceptOrder(@PathVariable Long orderId) {
        orderStatusComponent.acceptOrder(orderId);
    }

    @PostMapping("/{orderId}/reject")
    public void rejectOrder(@PathVariable Long orderId) {
        orderStatusComponent.rejectOrder(orderId);
    }

    @PostMapping("/{orderId}/prepare")
    public void preparingOrder(@PathVariable Long orderId) {
        orderStatusComponent.preparingOrder(orderId);
    }

    @PostMapping("/{orderId}/complete")
    public void completeOrder(@PathVariable Long orderId) {
        orderStatusComponent.completeOrder(orderId);
    }
}
