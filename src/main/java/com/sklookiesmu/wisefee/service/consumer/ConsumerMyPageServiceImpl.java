package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.domain.Subscribe;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerMyPageServiceImpl implements ConsumerMyPageService{
    private final SubscribeJpaRepository subscribeRepository;
    private final MemberRepository memberRepository;


    @Override
    public CafeDto.CafeListResponseDto getSubscribedCafe(Long memberId) {
        List<Cafe> subscribedCafes =  new ArrayList<>();
        memberRepository.findById(memberId);
        List<Subscribe> subscribesList =subscribeRepository.findAllByMemberId(memberId);
        for (Subscribe subscribe : subscribesList) {
            subscribedCafes.add(subscribe.getCafe());
        }
        return CafeDto.CafeListResponseDto.from(new SliceImpl<Cafe>(subscribedCafes, Pageable.unpaged(),false));
    }

    @Override
    public OrdersDto getAllOrderHistory(Long memberId) {
        memberRepository.findById(memberId);
        List<Subscribe> subscribesList =subscribeRepository.findAllByMemberId(memberId);
        List<Order> orders = new ArrayList<>();
        OrdersDto ordersDto = new OrdersDto();

        for (Subscribe subscribe : subscribesList) {
            orders.addAll(subscribe.getOrders());
        }
        for (Order order : orders) {
            ordersDto.addOrderResponseDto(OrderDto.OrderResponseDto.orderToDto(order));
        }
        return ordersDto;
    }
}
