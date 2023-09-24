package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersInfoDto;

public interface ConsumerOrderCheckingService {

    /**
     * [결재한 주문 내역 조회]
     * 현재 고객이 결재한 주문한 내역을 조회한다.
     * @return [주문 내역 정보 DTO 반환]
     */
    OrdersInfoDto getPaidOrdersHistory(Long memberId);
}
