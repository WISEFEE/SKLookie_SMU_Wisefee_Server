package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.ProductStatus;

import com.sklookiesmu.wisefee.common.exception.AuthForbiddenException;
import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;

import com.sklookiesmu.wisefee.common.exception.PreconditionFailException;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private final PaymentJpaRepository paymentJpaRepository;

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

        /* Create Order */
        Long memberId = SecurityUtil.getCurrentMemberPk();
        Cafe cafe = cafeJpaRepository.findById(cafeId)
                .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 매장입니다."));

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found"));

        Subscribe subscribe = subscribeJpaRepository.findByIdAndCafeId(orderRequestDto.getSubscribeId(), cafeId)
                .orElseThrow(()-> new NoSuchElementFoundException("존재하지 않는 구독권입니다"));

        if(subscribe.getMember().getMemberId() != SecurityUtil.getCurrentMemberPk()){
            throw new AuthForbiddenException("구독권의 소유자가 일치하지 않습니다.");
        }

        if (subscribe.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 구독권입니다.");
        }

        Order order = new Order();
        Payment payment = new Payment();

        Payment finalPayment = paymentJpaRepository.save(payment);

        order.setSubscribe(subscribe);
        order.setPayment(finalPayment);
        order.setProductStatus(ProductStatus.REQUESTED);
        order.setCreatedAt(LocalDateTime.now());
        orderJpaRepository.save(order);

        // 상품 주문
        List<OrderProduct> orderProducts = orderRequestDto.getOrderProduct().stream()
                .map(op -> {
                    Product product = productJpaRepository.findById(op.getProductId())
                            .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 상품입니다."));

                    if (!Objects.equals(product.getCafe().getCafeId(), cafeId)) {
                        throw new NoSuchElementFoundException("해당 카페에 존재하지 않는 상품입니다");
                    }

                    OrderProduct orderProduct = new OrderProduct();

                    orderProduct.setProduct(product);
                    orderProduct.setOrder(order);
                    orderProductJpaRepository.save(orderProduct);

                    // 상품옵션을 선택하지 않았을 시 continue
                    if(op.getProductOption() == null){
                        return null;
                    }

                    List<OrderProductOption> productOption = op.getProductOption().stream()
                            .map(prodOpt -> {

                                ProductOption productOptions = productOptJpaRepository.findById(prodOpt.getOrderProductOptionId())
                                        .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 상품옵션입니다."));

                                if (!Objects.equals(productOptions.getProduct().getCafe().getCafeId(), cafeId)) {
                                    throw new NoSuchElementFoundException("해당 카페에 존재하지 않는 상품옵션입니다");
                                }

                                OrderProductOption orderProductOption = new OrderProductOption();

                                orderProductOption.setProductOption(productOptions);
                                orderProductOption.setOrderProduct(orderProduct);
                                orderProductOptionJpaRepository.save(orderProductOption);


                                List<OrderProductOptionChoice> productOptChoice = prodOpt.getProductOptionChoices().stream()
                                        .map(prodOptChoice -> {

                                            ProductOptChoice productOptChoices = prodOptChoiceJpaRepository.findById(prodOptChoice.getOrderProductOptionChoiceId())
                                                    .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 상품선택옵션입니다."));

                                            if (!Objects.equals(productOptChoices.getProductOption().getProduct().getCafe().getCafeId(), cafeId)) {
                                                throw new NoSuchElementFoundException("해당 카페에 존재하지 않는 상품선택옵션입니다.");
                                            }

                                            OrderProductOptionChoice orderProductOptionChoice = OrderProductOptionChoice.createOrderProdOptChoice(orderProduct, orderProductOption, productOptChoices);
                                            orderProdOptChoiceJpaRepository.save(orderProductOptionChoice);

                                            return orderProductOptionChoice;
                                        }).collect(Collectors.toList());

                                OrderProductOption.createOrderProductOptionChoice(productOptChoice, orderProductOption);

                                return orderProductOption;
                            }).collect(Collectors.toList());

                    OrderProduct.createOrderProductOption(productOption, orderProduct);

                    return orderProduct;

                }).collect(Collectors.toList());

        int minPeople = subscribe.getSubTicketType().getSubTicketMinUserCount();
        int maxPeople = subscribe.getSubTicketType().getSubTicketMaxUserCount();

        log.info("주문제품 개수 : " + orderProducts.size());

        if (orderProducts.size() < minPeople) {
            throw new IllegalArgumentException("최소 " + minPeople +  "개 이상 주문해야합니다.");
        }

        if (orderProducts.size() > maxPeople) {
            throw new IllegalArgumentException("최대 " + maxPeople +  "개 이하 주문해야합니다.");
        }

        // 주문 옵션 설정
        List<OrdOrderOption> orderOptions = orderRequestDto.getOrderOption().stream()
                .map(oop -> {
                    OrderOption orderOption = orderOptionRepository.findById(oop.getOrderOptionId())
                            .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 주문 옵션입니다"));

                    if (!Objects.equals(orderOption.getCafe().getCafeId(), cafeId)) {
                        throw new IllegalArgumentException("해당 카페에 존재하지 않는 주문 옵션입니다");
                    }

                    OrdOrderOption ordOrderOption = OrdOrderOption.createOrdOrderOption(orderOption);
                    ordOrderOptionJpaRepository.save(ordOrderOption);

                    return ordOrderOption;

                }).collect(Collectors.toList());

        // 금액 계산 로직
        double totalPrice = 0;

        for (OrderProduct orderProduct : orderProducts) {

            log.info("제품주문 id : " +orderProduct.getOrder().getOrderId());
            // 제품 금액
            totalPrice += orderProduct.getProduct().getProductPrice();
            log.info("제품가격 : " + totalPrice);

            // 제품 옵션 추가 금액
            totalPrice += orderProduct.getOrderProductOptions()
                    .stream()
                    .mapToInt(option -> option.getOrderProductOptChoice().stream()
                            .mapToInt(optionChoice -> optionChoice.getProductOptChoice().getProductOptionChoicePrice())
                            .sum())
                    .sum();

            log.info("제품옵션추가된가격 : " + totalPrice);
        }


        // 주문 옵션 추가 금액
        for(OrdOrderOption ordOrderOption :orderOptions){
            log.info("주문 옵션 추가 금액 : " + ordOrderOption.getOrderOption().getOrderOptionPrice());

            totalPrice += ordOrderOption.getOrderOption().getOrderOptionPrice();
        }

        int subTicketDeposit = subscribe.getSubTicketType().getSubTicketDeposit() * subscribe.getSubPeople(); //  텀블러 보증금

        finalPayment.setPaymentPrice(discountPayment(totalPrice, subscribe)+subTicketDeposit);

        log.info("할인된 금액: " + (discountPayment(totalPrice, subscribe)));
        log.info("할인+보증금 총금액: " + (discountPayment(totalPrice, subscribe)+subTicketDeposit));
        finalPayment.setCreatedAt(LocalDateTime.now());

        Order.createOrder(order, orderProducts, orderOptions);

        // TODO : 결제수단 생성
        finalPayment.setPaymentMethod(orderRequestDto.getPaymentMethod().getMethod());

        return order.getOrderId();
    }

    /**
     * 주문내역 조회
     * @param orderId
     * @return OrderResponseDto
     */
    @Override
    public OrderDto.OrderResponseDto getOrderHistory(Long orderId) {
        Order order = orderJpaRepository.findById(orderId)
                .orElseThrow(()->new NoSuchElementFoundException("존재하지 않는 주문입니다."));
        return OrderDto.OrderResponseDto.orderToDto(order);
    }


    /**
     * 결제수단 생성
     */
    /*@Transactional
    @Override
    public Long createPaymentMethod(Long cafeId, Long orderId, PaymentDto.PaymentRequestDto paymentRequestDto) {
        Cafe cafe = cafeJpaRepository.findById(cafeId)
                .orElseThrow(()->new NoSuchElementFoundException("존재하지 않는 카페입니다."));

        Order order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 주문내역입니다."));

        Payment payment = paymentJpaRepository.findById(order.getPayment().getPaymentId())
                .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 금액입니다"));


        if (paymentRequestDto.getPaymentPrice() != payment.getPaymentPrice()) {
            throw new PreconditionFailException("결제금액이 일치하지 않습니다");
        } else {
            payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        }

        return payment.getPaymentId();
    } */

    /**
     * 주문 내역 금액 조회
     */
    @Override
    public PaymentDto.PaymentResponseDto getPayment(Long orderId){

        Order order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 주문내역입니다."));

       /*Subscribe subscribe = subscribeJpaRepository.findById(order.getSubscribe().getSubId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 구독권입니다."));*/

        Payment payment = paymentJpaRepository.findById(order.getPayment().getPaymentId())
                .orElseThrow(() -> new NoSuchElementFoundException("존재하지 않는 주문금액입니다"));

        return PaymentDto.PaymentResponseDto.from(payment);
    }

    public long discountPayment(double price, Subscribe subscribe) {

        long result = 0L;

        SubTicketType subTicketType = subscribe.getSubTicketType();
        double subPeople = subscribe.getSubPeople();
        double subTicketAdditionalDiscountRate = subTicketType.getSubTicketAdditionalDiscountRate() * subPeople; // 인원당 추가 할인율
        double subTicketBaseDiscountRate = subTicketType.getSubTicketBaseDiscountRate(); // 기본 할인율
        double subTicketMaxDiscountRate = subTicketType.getSubTicketMaxDiscountRate(); // 최대 할인율
        double totalDiscountRate = ((subTicketBaseDiscountRate + subTicketAdditionalDiscountRate) / 100);
        double currentDiscountRate = Math.min(totalDiscountRate, subTicketMaxDiscountRate / 100);
        log.info("적용될 할인율 : " + currentDiscountRate);
        result += price - (price * currentDiscountRate);

        return result;
    }
}
