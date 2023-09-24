package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.domain.Subscribe;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderInfoDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersInfoDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerOrderCheckingServiceImpl implements ConsumerOrderCheckingService{
    private final MemberRepository memberRepository;
    private final SubscribeJpaRepository subscribeRepository;

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
