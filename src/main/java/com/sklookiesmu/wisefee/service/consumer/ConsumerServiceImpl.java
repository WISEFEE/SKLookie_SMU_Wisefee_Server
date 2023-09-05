package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.subscribe.PaymentJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubTicketTypeJpaRepository;
import com.sklookiesmu.wisefee.repository.subscribe.SubscribeJpaRepository;
import com.sklookiesmu.wisefee.repository.cafe.CafeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final CafeJpaRepository cafeJpaRepository;
    private final SubscribeJpaRepository subscribeRepository;
    private final SubTicketTypeJpaRepository subTicketTypeRepository;
    private final PaymentJpaRepository paymentJpaRepository;
    private final MemberRepository memberRepository;


    /**
     * 정기구독 체결
     */
    @Override
    public void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId, Long memberId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();
        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId).orElseThrow();
        Payment payment = new Payment();
        payment.setPaymentPrice(subTicketType.getSubTicketPrice());
        paymentJpaRepository.save(payment);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found")) ;
        subscribeRepository.save(request.toEntity(cafe, subTicketType, payment,member));
    }

    /**
     * 정기구독 내역 조회
     * @param memberId
     * @return SubScribeResponseDto
     */
    @Override
    public SubscribeDto.SubscribeListResponseDto getSubscribe(Long memberId) {

        // 유저 검증 필요
        memberRepository.findById(memberId);

        List<Subscribe> list =subscribeRepository.findAllByMemberId(memberId);

        return SubscribeDto.SubscribeListResponseDto.of(list);
    }

    /**
     * 정기구독 결제 -> 필요없을지도..
     */
    @Override
    public void createPayment(PaymentDto.PaymentRequestDto request, Long cafeId, Long subTicketTypeId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();
        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId).orElseThrow();

        paymentJpaRepository.save(request.toEntity(subTicketType));
    }

    /**
     * 정기구독 해지
     */
    @Override
    public void cancelSubscribe(Long memberId) {
        //Long memberId = SecurityUtil.getCurrentMemberPk();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found")) ;
        Subscribe subscribe = subscribeRepository.findByMemberAndSubStatus(member, "Y")
                .orElseThrow(() -> new IllegalArgumentException("구독권이 존재하지 않습니다."));

        subscribeRepository.deleteById(subscribe.getSubId()); // TODO 환불 조건....
    }
}
