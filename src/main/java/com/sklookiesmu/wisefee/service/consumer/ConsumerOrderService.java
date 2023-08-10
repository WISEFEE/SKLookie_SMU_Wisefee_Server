package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;

public interface ConsumerOrderService {
    /**
     * [주문 옵션 정보 조회]
     * 특정 매장의 주문 옵션 정보를 조회한다.
     * @param cafeId
     * @return [주문 옵션 정보 DTO 반환]
     */
    OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId);


    /**
     * TODO [주문하기] ->
     * 장바구니 기능 설계된 후에 구현 필요
     * IA 정보 구조도 => 장바구니에 담겨진 제품들만 주문 가능
     */
    //void createOrder();


    /**
     * TODO [주문 내역 조회]
     */


    /**
     * TODO [주문 현황 확인] -> 실시간 통신 필요 (카페 측 음료 준비 현황)
     */
}
