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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Transactional
    @Override
    public Long createSubscribe(SubscribeDto.SubscribeRequestDto request, Long cafeId, Long subTicketTypeId, Long memberId) {

        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow();

        // 한 카페에서 한 종류의 구독권만 체결 가능

        Subscribe subscribe = subscribeRepository.findByCafeIdAndMemberId(cafeId, memberId);
        if (subscribe != null && subscribe.getExpiredAt().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("이미 구독하셨습니다.");
        }

        SubTicketType subTicketType = subTicketTypeRepository.findById(subTicketTypeId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 구독권 종류입니다."));
        Payment payment = new Payment();
        payment.setPaymentPrice(subTicketType.getSubTicketPrice());
        payment.setPaymentMethod(request.getPaymentMethod());
        paymentJpaRepository.save(payment);

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found")) ;

        return subscribeRepository.save(request.toEntity(cafe, subTicketType, payment, member)).getSubId();
    }

    /**
     * 정기구독 내역 조회
     * @param memberId
     * @return SubScribeResponseDto
     */
    @Override
    public SubscribeDto.SubscribeListResponseDto getSubscribe(Long memberId) {

        // 유저 검증 필요
        memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementFoundException("member not found"));

        List<Subscribe> list =subscribeRepository.findAllByMemberId(memberId);

        return SubscribeDto.SubscribeListResponseDto.of(list);
    }

    /**
     * 정기구독 해지
     */
    @Transactional
    @Override
    public void cancelSubscribe(Long memberId, Long subscribeId) {
        Subscribe subscribe = subscribeRepository.findByMemberIdAndSubscribeId(memberId, subscribeId)
                .orElseThrow(() -> new IllegalArgumentException("구독권이 존재하지 않습니다."));

        subscribeRepository.deleteById(subscribe.getSubId()); // TODO 환불 조건....
    }
}
