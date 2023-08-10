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
    void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId, Long userId);

    /**
     * [구독 조회]
     * 고객이 구독한 내역과 결제 내역을 조회한다.
     * @param memberId
     * @return SubScribeResponseDto
     */
    SubscribeDto.SubscribeListResponseDto getSubscribe(Long memberId);

    /**
     * [구독 결제]
     * 선택한 정기 구독권을 결제한다.
     * @param request
     */
    void createPayment(PaymentDto.PaymentRequestDto request, Long cafeId, Long subscribeId);


    /**
     * TODO [정기구독 해지]
     */
    void cancelSubscribe(Long memberId);

    /**
     * TODO [정기 구독 결제 취소]
     */
}
