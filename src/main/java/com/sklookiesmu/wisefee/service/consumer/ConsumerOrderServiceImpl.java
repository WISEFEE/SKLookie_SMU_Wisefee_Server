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
import com.sklookiesmu.wisefee.repository.subscribe.PaymentJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final OrdOrderOptionJpaRepository ordOrderOptionJpaRepository;
    private final OrderProductOptionJpaRepository orderProductOptionJpaRepository;
    private final OrderProdOptChoiceJpaRepository orderProdOptChoiceJpaRepository;

    Order order = new Order();
    Product product = new Product();
    OrderProduct orderProduct = new OrderProduct();
    ProductOption productOptions = new ProductOption();
    OrderProductOption orderProductOption = new OrderProductOption();
    ProductOptChoice productOptChoices = new ProductOptChoice();
    OrderProductOptionChoice orderProductOptionChoice = new OrderProductOptionChoice();
    OrdOrderOption ordOrderOption = new OrdOrderOption();

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

        order.setSubscribe(subscribe);
        order.setPayment(order.getSubscribe().getPayment());
        order.setProductStatus(ProductStatus.REQUESTED);
        order.setCreatedAt(LocalDateTime.now());
        Order ord = orderJpaRepository.save(order);
        log.info("orderID : " + ord.getOrderId());
        // 상품 주문
        Order finalOrd = ord;
        List<OrderProduct> orderProducts = orderRequestDto.getOrderProduct().stream()
                .map(op -> {
                    product = productJpaRepository.findById(op.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    orderProduct.setProduct(product);
                    orderProduct.setOrder(finalOrd);
                    orderProductJpaRepository.save(orderProduct);

                    List<OrderProductOption> productOption = op.getProductOption().stream()
                            .map(prodOpt -> {
                                log.info("OrderProductOption : " + prodOpt.getOrderProductOptionId());

                                productOptions = productOptJpaRepository.findById(prodOpt.getOrderProductOptionId())
                                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품옵션입니다."));

                                orderProductOption.setProductOption(productOptions);
                                orderProductOption.setOrderProduct(orderProduct);
                                orderProductOptionJpaRepository.save(orderProductOption);

                                List<OrderProductOptionChoice> productOptChoice = prodOpt.getProductOptionChoices().stream()
                                        .map(prodOptChoice -> {
                                            log.info("OrderProductOptionChoice : " + prodOptChoice.getOrderProductOptionChoiceId());

                                            productOptChoices = prodOptChoiceJpaRepository.findById(prodOptChoice.getOrderProductOptionChoiceId())
                                                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품선택옵션입니다."));

                                            orderProductOptionChoice = OrderProductOptionChoice.createOrderProdOptChoice(orderProduct, orderProductOption, productOptChoices);
                                            orderProdOptChoiceJpaRepository.save(orderProductOptionChoice);

                                            return OrderProductOptionChoice.builder()
                                                    .orderProductOptionChoiceId(productOptChoices.getProductOptionChoiceId())
                                                    .build();
                                        }).collect(Collectors.toList());

                                orderProductOption = OrderProductOption.createOrderProductOptionChoice(productOptChoice);

                                return OrderProductOption.builder()
                                        .orderProductOptionId(productOptions.getProductOptionId())
                                        .build();
                            }).collect(Collectors.toList());

                    orderProduct = OrderProduct.createOrderProductOption(productOption);
                    return orderProduct;

                }).collect(Collectors.toList());

        // 주문 옵션 설정
        List<OrdOrderOption> orderOptions = orderRequestDto.getOrderOption().stream()
                .map(oop -> {
                    OrderOption orderOption = orderOptionRepository.findById(oop.getOrderOptionId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 옵션입니다"));

                    ordOrderOption = OrdOrderOption.createOrdOrderOption(orderOption);

                    return ordOrderOption;

                }).collect(Collectors.toList());

        Order.createOrder(ord, orderProducts, orderOptions);
        log.info("ordID : " + ord.getOrderId());
        log.info("orderProductChoice : " + ord.getOrderProducts().get(0).getOrderProductOptionChoice().size());
        log.info("orderProductOptions : " + ord.getOrderProducts().get(0).getOrderProductOptions().size());


       /* OrderProduct orderProd = orderProductJpaRepository.findById(orderProduct.getOrderProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 상품입니다"));
*/

        ordOrderOptionJpaRepository.save(ordOrderOption);

        Order finalOrder = orderJpaRepository.findById(ord.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문니다"));
        return orderJpaRepository.save(finalOrder).getOrderId();
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
