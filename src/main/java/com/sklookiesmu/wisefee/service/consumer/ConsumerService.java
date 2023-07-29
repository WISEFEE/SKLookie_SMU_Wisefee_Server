package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;


public interface ConsumerService{

    /**
     * [구독하기]
     * 고객이 매장의 구독권을 구독한다.
     * @param request
     */
    void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId);

}
