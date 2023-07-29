package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import com.sklookiesmu.wisefee.repository.subscribe.SubTicketTypeRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final CafeJpaRepository cafeJpaRepository;
    private final SubscribeRepository subscribeRepository;
    private final SubTicketTypeRepository subTicketTypeRepository;


    /**
     * 정기구독 체결
     */
    @Override
    public void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();
        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId).orElseThrow();

        subscribeRepository.save(request.toEntity(cafe, subTicketType));
    }
}
