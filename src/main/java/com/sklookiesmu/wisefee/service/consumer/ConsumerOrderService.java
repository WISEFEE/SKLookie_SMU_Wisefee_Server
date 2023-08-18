package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;

public interface ConsumerOrderService {
    /**
     * [주문 옵션 정보 조회]
     * 특정 매장의 주문 옵션 정보를 조회한다.
     * @param cafeId
     * @return [주문 옵션 정보 DTO 반환]
     */
    OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId);


    /**
     * TODO [주문하기]
     */
    Long createOrder(Long cafeId, OrderDto.OrderRequestDto orderRequestDto);


    /**
     * TODO [주문 내역 조회]
     */
    OrderDto.OrderResponseDto getOrderHistory(Long cafeId, Long orderId);


    /**
     * TODO [주문 현황 확인] -> 동기 비동기?(카페 측 음료 준비 현황)
     */


    /**
     * TODO [주문 취소하기] -> 매장 측에서 접수완료하기 전에만 가능
     */

}
