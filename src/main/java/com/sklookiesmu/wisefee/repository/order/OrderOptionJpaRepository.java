package com.sklookiesmu.wisefee.repository.order;

import com.sklookiesmu.wisefee.domain.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderOptionJpaRepository extends JpaRepository<OrderOption, Long> {

    /**
     * [주문 옵션 정보 조회 쿼리]
     * 매장 PK와 주문옵션과 연관관계를 가진 매장의 PK 일치 여부를 통해 옵션정보를 조회한다.
     * @param cafeId
     * @return
     */
    @Query(value = "select op from OrderOption op where op.cafe.cafeId = :cafeId")
    List<OrderOption> findOptionByCafeId(@Param("cafeId") Long cafeId);

}
