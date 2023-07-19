package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderOptionRepository extends JpaRepository<OrderOption, Long> {

    @Query(value = "select op from OrderOption op where op.cafe.cafeId = :cafeId")
    Optional<OrderOption> findOptionByCafeId(@Param("cafeId") Long cafeId);

}
