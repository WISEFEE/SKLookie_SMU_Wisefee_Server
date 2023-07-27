package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.domain.Subscribe;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.CafeProductDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import org.springframework.data.domain.Pageable;

public interface ConsumerService{

    /**
     * [매장 리스트 조회]
     * 매장 리스트를 조회하고, 이름순으로 조회 가능하도록 한다.
     * @param pageable
     * @return [매장 리스트 반환]
     */
    CafeDto.CafeListResponseDto getCafeList(Pageable pageable);

    /**
     * [매장의 음료 제품 리스트 조회]
     * 매장의 PK를 통해서 매장 음료 리스트를 조회한다.
     * @param cafeId
     * @return [음료 제품 리스트 반환]
     */
    CafeProductDto.CafeProductListResponseDto getProductList(Long cafeId);

    /**
     * [매장 정보 조회]
     * 특정 매장 정보를 조회한다.
     * @param cafeId
     * @return [매장 정보 DTO 반환]
     */
    CafeDto.CafeResponseDto getCafeInfo(Long cafeId);

    /**
     * [주문 옵션 정보 조회]
     * 특정 매장의 주문 옵션 정보를 조회한다.
     * @param cafeId
     * @return [주문 옵션 정보 DTO 반환]
     */
    OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId);

    /**
     * [구독하기]
     * 고객이 매장의 구독권을 구독한다.
     * @param request
     */
    void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId);

}
