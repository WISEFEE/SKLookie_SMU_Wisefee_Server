package com.sklookiesmu.wisefee.service.consumer;

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
    public void createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();
        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId).orElseThrow();
        Payment payment = new Payment();
        payment.setPaymentPrice(subTicketType.getSubTicketPrice());
        paymentJpaRepository.save(payment);

        // 유저 검증 필요
        Member member = memberRepository.find(1L);
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
        memberRepository.find(memberId);

        List<Subscribe> list =subscribeRepository.findAllByMemberId(memberId);

        return SubscribeDto.SubscribeListResponseDto.of(list);
    }

    /**
     * 정기구독 결제
     */
    @Override
    public void createPayment(PaymentDto.PaymentRequestDto request, Long cafeId, Long subTicketTypeId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();
        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId).orElseThrow();

        paymentJpaRepository.save(request.toEntity(subTicketType));
    }
}
