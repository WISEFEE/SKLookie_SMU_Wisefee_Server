package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.repository.order.OrderRepository;
import com.sklookiesmu.wisefee.service.seller.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, ProductStatus newStatus) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new NoSuchElementFoundException("존재하지 않는 주문입니다.");
        }

        order.setProductStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByCafeId(Long cafeId) {
        return orderRepository.findAllBySubscribeCafeCafeId(cafeId);
    }
}
