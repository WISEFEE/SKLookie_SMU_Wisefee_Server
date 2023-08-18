package com.sklookiesmu.wisefee.repository.order;

import com.sklookiesmu.wisefee.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
