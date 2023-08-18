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
import com.sklookiesmu.wisefee.repository.product.OrderProductJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProdOptChoiceJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptJpaRepository;
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
    private final ProductOptJpaRepository productOptJpaRepository;
    private final ProdOptChoiceJpaRepository prodOptChoiceJpaRepository;
    private final OrderProductJpaRepository orderProductJpaRepository;

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
    // TODO : 코드 리팩토링 필요 !
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

                    List<ProductOption> productOption = op.getProductOption().stream()
                            .map(prodOpt -> {
                                ProductOption productOptions = productOptJpaRepository.findById(prodOpt.getProductOptionId())
                                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품옵션입니다."));

                                List<ProductOptChoice> productOptChoice = prodOpt.getProductOptionChoice().stream()
                                        .map(prodOptChoice -> {
                                            ProductOptChoice productOptChoices = prodOptChoiceJpaRepository.findById(prodOptChoice.getOptionChoiceId())
                                                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품선택옵션입니다."));

                                            return ProductOptChoice.builder()
                                                    .productOptionChoiceId(productOptChoices.getProductOptionChoiceId())
                                                    .build();
                                        }).collect(Collectors.toList());

                                return ProductOption.builder()
                                        .productOptionId(productOptions.getProductOptionId())
                                        .build();

                            }).collect(Collectors.toList());

                    OrderProduct orderProduct = OrderProduct.createOrderProduct(product);
                    orderProductJpaRepository.save(orderProduct);

                    return orderProduct;

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
}
