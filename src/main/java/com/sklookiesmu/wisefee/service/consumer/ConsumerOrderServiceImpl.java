package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.repository.order.OrderOptionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerOrderServiceImpl implements ConsumerOrderService{

    private final OrderOptionJpaRepository orderOptionRepository;

    /**
     * 매장 상세보기 -> 주문 옵션 정보
     */
    @Override
    public OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId){
        OrderOption orderOption = orderOptionRepository.findOptionByCafeId(cafeId).orElseThrow();
        return OrderOptionDto.OrderOptionResponseDto.from(orderOption);
    }
}
