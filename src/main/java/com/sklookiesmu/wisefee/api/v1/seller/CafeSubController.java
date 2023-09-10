package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.SubMemberDetailsDto;
import com.sklookiesmu.wisefee.dto.seller.SubMemberDto;
import com.sklookiesmu.wisefee.service.seller.CafeService;
import com.sklookiesmu.wisefee.service.seller.OrderService;
import com.sklookiesmu.wisefee.service.shared.interfaces.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "SELL-E :: 매장 구독자 정보 API")
@RestController
@RequiredArgsConstructor
public class CafeSubController {

    private final CafeService cafeService;
    private final MemberService memberService;
    private final OrderService orderService;

    @ApiOperation(
            value = "SELL-E-05 :: 매장 구독 멤버 목록 조회",
            notes = "매장에 구독한 멤버 목록을 조회하는 API입니다."
    )
    @GetMapping("/api/v1/seller/cafe/{cafeId}/subscribers")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public List<SubMemberDto> getCafeSubscribers(@PathVariable("cafeId") Long cafeId) {
        List<Subscribe> subscriptions = cafeService.getSubscribersByCafeId(cafeId);

        return subscriptions.stream()
                .map(subscription -> SubMemberDto.fromMemberAndSubscribe(subscription.getMember(), subscription, subscription.getSubTicketType()))
                .collect(Collectors.toList());
    }

    @ApiOperation(
            value = "SELL-E-06 :: 매장 구독 멤버 세부 정보 조회",
            notes = "매장에 구독한 멤버의 세부 정보를 조회하는 API입니다."
    )
    @GetMapping("/api/v1/seller/cafe/{cafeId}/subscribers/{memberId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public SubMemberDetailsDto getSubMemberDetails(@PathVariable("cafeId") Long cafeId,
                                                   @PathVariable("memberId") Long memberId) {

        List<Subscribe> subscriptions = cafeService.getSubscribersByCafeId(cafeId);

        Optional<Subscribe> subscriptionOptional = subscriptions.stream()
                .filter(subscription -> subscription.getMember().getMemberId().equals(memberId))
                .findFirst();

        if (subscriptionOptional.isPresent()) {
            Subscribe subscribe = subscriptionOptional.get();
            return SubMemberDetailsDto.fromSubscriptionAndMember(subscribe);
        } else {
            throw new NoSuchElementFoundException("구독 정보를 찾을 수 없습니다.");
        }

    }
}
