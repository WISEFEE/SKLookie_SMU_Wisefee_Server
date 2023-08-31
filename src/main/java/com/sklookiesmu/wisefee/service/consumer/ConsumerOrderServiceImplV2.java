package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeJpaRepository;
import com.sklookiesmu.wisefee.repository.order.*;
import com.sklookiesmu.wisefee.repository.product.OrderProductJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProdOptChoiceJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductOptJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerOrderServiceImplV2 implements ConsumerOrderService{

    private final OrderOptionJpaRepository orderOptionRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final CafeJpaRepository cafeJpaRepository;
    private final SubscribeJpaRepository subscribeJpaRepository;
    private final MemberRepository memberRepository;
    private final ProductJpaRepository productJpaRepository;
    private final ProductOptJpaRepository productOptJpaRepository;
    private final ProdOptChoiceJpaRepository prodOptChoiceJpaRepository;
    private final OrderProductJpaRepository orderProductJpaRepository;
    private final OrdOrderOptionJpaRepository ordOrderOptionJpaRepository;
    private final OrderProductOptionJpaRepository orderProductOptionJpaRepository;
    private final OrderProdOptChoiceJpaRepository orderProdOptChoiceJpaRepository;

//    Order order = new Order();
//    Product product = new Product();
//    OrderProduct orderProduct = new OrderProduct();
//    ProductOption productOptions = new ProductOption();
//    OrderProductOption orderProductOption = new OrderProductOption();
//    ProductOptChoice productOptChoices = new ProductOptChoice();
//    OrderProductOptionChoice orderProductOptionChoice = new OrderProductOptionChoice();
//    OrdOrderOption ordOrderOption = new OrdOrderOption();

    /**
     * 매장 상세보기 -> 주문 옵션 정보
     */
    @Override
    public OrderOptionDto.OrderOptionListResponseDto getOrderOptionInfo(Long cafeId){
        List<OrderOption> orderOptionList = orderOptionRepository.findOptionByCafeId(cafeId);
        return OrderOptionDto.OrderOptionListResponseDto.of(orderOptionList);
    }

    /**
     * 주문하기
     * @return orderId
     */
    @Override
    @Transactional
    public Long createOrder(Long cafeId, OrderDto.OrderRequestDto orderRequestDto) {


        /* Create Order */
        Long memberId = SecurityUtil.getCurrentMemberPk();
        Cafe cafe = cafeJpaRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매장입니다."));

        Member member = memberRepository.find(memberId);
        Subscribe subscribe = subscribeJpaRepository.findByMemberAndCafe(member, cafe)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 구독권입니다."));

        Order order = new Order();
        order.setSubscribe(subscribe);
        order.setPayment(order.getSubscribe().getPayment());
        order.setProductStatus(ProductStatus.REQUESTED);
        order.setCreatedAt(LocalDateTime.now());
        orderJpaRepository.save(order);


        /* 상품 */
        List<OrderProduct> orderProducts = orderRequestDto.getOrderProduct().stream()
                .map(op -> {
                    Product product = productJpaRepository.findById(op.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(product);
                    orderProduct.setOrder(order);
                    orderProductJpaRepository.save(orderProduct);

                    /* 상품 옵션 */
                    OrderProduct finalOrderProduct = orderProduct;

                    // 상품옵션을 선택하지 않았을 시 continue
                    if(op.getProductOption() == null){
                        return null;
                    }
                    List<OrderProductOption> productOption = op.getProductOption().stream()
                            .map(prodOpt -> {

                                ProductOption productOptions = productOptJpaRepository.findById(prodOpt.getOrderProductOptionId())
                                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품옵션입니다."));


                                OrderProductOption orderProductOption = new OrderProductOption();
                                orderProductOption.setProductOption(productOptions);
                                orderProductOption.setOrderProduct(finalOrderProduct);
                                orderProductOptionJpaRepository.save(orderProductOption);

                                /* 상품 옵션 선택지 */
                                OrderProductOption finalOrderProductOption = orderProductOption;
                                List<OrderProductOptionChoice> productOptChoice = prodOpt.getProductOptionChoices().stream()
                                        .map(prodOptChoice -> {

                                            ProductOptChoice productOptChoices = prodOptChoiceJpaRepository.findById(prodOptChoice.getOrderProductOptionChoiceId())
                                                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품선택옵션입니다."));

                                            OrderProductOptionChoice orderProductOptionChoice = new OrderProductOptionChoice();
                                            orderProductOptionChoice = OrderProductOptionChoice.createOrderProdOptChoice(finalOrderProduct, finalOrderProductOption, productOptChoices);
                                            orderProdOptChoiceJpaRepository.save(orderProductOptionChoice);

                                            return OrderProductOptionChoice.builder()
                                                    .orderProductOptionChoiceId(productOptChoices.getProductOptionChoiceId())
                                                    .build();
                                        }).collect(Collectors.toList());

                                OrderProductOption.createOrderProductOptionChoice(productOptChoice);

                                return OrderProductOption.builder()
                                        .orderProductOptionId(productOptions.getProductOptionId())
                                        .build();
                            }).collect(Collectors.toList());

                    orderProduct = OrderProduct.createOrderProductOption(productOption);
                    return orderProduct;

                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        /* 주문 옵션 */
        List<OrdOrderOption> orderOptions = orderRequestDto.getOrderOption().stream()
                .map(oop -> {
                    OrderOption orderOption = orderOptionRepository.findById(oop.getOrderOptionId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 옵션입니다"));

                    OrdOrderOption ordOrderOption;
                    ordOrderOption = OrdOrderOption.createOrdOrderOption(orderOption);

                    ordOrderOptionJpaRepository.save(ordOrderOption);
                    return ordOrderOption;

                }).collect(Collectors.toList());

        Order.createOrder(order, orderProducts, orderOptions);

        return order.getOrderId();
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
