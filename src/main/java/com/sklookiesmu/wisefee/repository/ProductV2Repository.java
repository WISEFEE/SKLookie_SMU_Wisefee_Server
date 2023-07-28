package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductV2Repository extends JpaRepository<Product, Long> {

    /**
     * [매장 음료제품 리스트 조회]
     * 제품과 연관관계를 가지고 있는 매장의 PK를 통해 해당하는 매장의 음료 제품 리스트를 조회한다.
     * @param cafeId
     * @return [매장 음료 리스트]
     */
    @Query(value = "select p from Product p where p.cafe.cafeId = :cafeId")
    List<Product> findAllByCafeId(@Param("cafeId") Long cafeId);
}
