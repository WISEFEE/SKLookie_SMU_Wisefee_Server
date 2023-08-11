package com.sklookiesmu.wisefee.repository.order;

import com.sklookiesmu.wisefee.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public Order findById(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public Order save(Order order) {
        if (order.getOrderId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
        return order;
    }
}
