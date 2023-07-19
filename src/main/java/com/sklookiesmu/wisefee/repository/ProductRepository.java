package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
