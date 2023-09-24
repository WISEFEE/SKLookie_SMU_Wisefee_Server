package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Payment;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;


public interface ConsumerService{

    /**
     * [구독하기]
     * 고객이 매장의 구독권을 구독한다.
     * @param request
     */
    Long createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId, Long userId);

    /**
     * [구독 조회]
     * 고객이 구독한 내역과 결제 내역을 조회한다.
     * @param memberId
     * @return SubScribeResponseDto
     */
    SubscribeDto.SubscribeListResponseDto getSubscribe(Long memberId);



    /**
     * [진행중인 구독 조회]
     * 고객이 진행중인 구독한 내역과 결제 내역을 조회한다.
     * @param memberId
     * @return SubScribeResponseDto
     */
    SubscribeDto.SubscribeListResponseDto getSubscribeCurrent(Long memberId);

    /**
     * [정기구독 해지]
     * 구독하고 있는 정기 구독권을 해지한다.
     */
    void cancelSubscribe(Long memberId, Long subscribeId);
}
