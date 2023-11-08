package com.sklookiesmu.wisefee.repository.product;

import com.sklookiesmu.wisefee.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
}
