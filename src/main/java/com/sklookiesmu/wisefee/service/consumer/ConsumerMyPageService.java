package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.dto.consumer.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ConsumerMyPageService {

    /**
     * [구독권 조회]
     * 구독한 카페 내역을 조회하도록 한다.
     * @return [구독 내역 반환]
     */
    CafeDto.CafeListResponseDto getSubscribedCafe(Long memberId);
    /**
     * [주문내역 조회]
     * 주문 내역을 조회하도록 한다.
     * @return [주문 내역 반환]
     */
    OrdersInfoDto getAllOrderHistory(Long memberId);


    /**
     * [결재한 주문 내역 조회]
     * 현재 고객이 결재한 주문한 내역을 조회한다.
     * @return [주문 내역 정보 DTO 반환]
     */
    OrdersInfoDto getPaidOrdersHistory(Long memberId);

    /**
     * TODO [알람 범위 설정]
     */
}
