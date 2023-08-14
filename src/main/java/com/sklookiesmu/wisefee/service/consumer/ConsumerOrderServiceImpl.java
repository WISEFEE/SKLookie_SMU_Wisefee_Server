package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeJpaRepository;
import com.sklookiesmu.wisefee.repository.order.OrderJpaRepository;
import com.sklookiesmu.wisefee.repository.order.OrderOptionJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.PaymentJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerOrderServiceImpl implements ConsumerOrderService{

    private final OrderOptionJpaRepository orderOptionRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final CafeJpaRepository cafeJpaRepository;
    private final SubscribeJpaRepository subscribeJpaRepository;
    private final MemberRepository memberRepository;
    private final ProductJpaRepository productJpaRepository;

    /**
     * 매장 상세보기 -> 주문 옵션 정보
     */
    @Override
    public OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId){
        OrderOption orderOption = orderOptionRepository.findOptionByCafeId(cafeId).orElseThrow();
        return OrderOptionDto.OrderOptionResponseDto.from(orderOption);
    }

    /**
     * 주문하기
     * @return orderId
     */
    @Override
    @Transactional
    public Long createOrder(Long cafeId, OrderDto.OrderRequestDto orderRequestDto) {
        Long memberId = SecurityUtil.getCurrentMemberPk();
        Cafe cafe = cafeJpaRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

        Member member = memberRepository.find(memberId);
        Subscribe subscribe = subscribeJpaRepository.findByMemberAndCafe(member, cafe)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구독권입니다."));


        List<OrderProduct> orderProducts = orderRequestDto.getOrderProduct().stream()
                .map(op -> {
                    Product product = productJpaRepository.findById(op.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
                    return OrderProduct.builder()
                            .product(product)
                            .build();
                }).collect(Collectors.toList());


        Order order = Order.createOrder(subscribe, orderProducts);

        return orderJpaRepository.save(order).getOrderId();
    }

    @Override
    public OrderDto.OrderResponseDto getOrderHistory(Long cafeId, Long orderId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카페입니다."));

        Order order = orderJpaRepository.findById(orderId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 주문입니다"));
        return OrderDto.OrderResponseDto.orderToDto(order);
    }

    public void createOrderProduct(OrderDto.OrderProductRequestDto orderProductRequestDto) {

    }
}
