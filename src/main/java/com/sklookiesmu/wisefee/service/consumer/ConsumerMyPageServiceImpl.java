package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.domain.Subscribe;
import com.sklookiesmu.wisefee.dto.consumer.*;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

            if(subscribe.getExpiredAt().isAfter(LocalDateTime.now())){
                subscribedCafes.add(subscribe.getCafe());
            }

        }
        return CafeDto.CafeListResponseDto.from(new SliceImpl<Cafe>(subscribedCafes, Pageable.unpaged(),false));
    }

    @Override
    public OrdersInfoDto getAllOrderHistory(Long memberId) {
        memberRepository.findById(memberId);
        List<Subscribe> subscribesList =subscribeRepository.findAllByMemberId(memberId);
        List<Order> orders = new ArrayList<>();
        OrdersInfoDto ordersInfoDto = new OrdersInfoDto();

        for (Subscribe subscribe : subscribesList) {
            orders.addAll(subscribe.getOrders());
        }
        for (Order order : orders) {
            ordersInfoDto.addOrderInfoResponseDto(OrderInfoDto.OrderInfoResponseDto.orderToDto(order));
        }
        return ordersInfoDto;
    }

    @Override
    public OrdersInfoDto getPaidOrdersHistory(Long memberId) {
        memberRepository.findById(memberId);
        List<Subscribe> subscribesList =subscribeRepository.findAllByMemberId(memberId);
        List<Order> orders = new ArrayList<>();
        OrdersInfoDto ordersInfoDto = new OrdersInfoDto();

        for (Subscribe subscribe : subscribesList) {
            orders.addAll(subscribe.getOrders());
        }
        for (Order order : orders) {
            if(order.getProductStatus() != ProductStatus.DONE){
                ordersInfoDto.addOrderInfoResponseDto(OrderInfoDto.OrderInfoResponseDto.orderToDto(order));
            }
        }
        return ordersInfoDto;
    }
}
