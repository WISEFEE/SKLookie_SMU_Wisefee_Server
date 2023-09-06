package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "CONS-B :: 정기구독 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerController {

    private final ConsumerServiceImpl consumerService;

    @ApiOperation(value = "CONS-B-01 :: 정기 구독하기",
            notes = "정기 구독을 요청합니다. cafeId에는 구독할 카페를, subTicketId에는, COMM-D의 구독권 유형 종류를 넣어주시면 됩니다.<br><br>" +
                    "paymentMethod에는 결제수단을, subComment에는 구독 요청사항을, subPeople에는 구독 인원을 입력해주세요." )

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @PostMapping("/subscribe/{cafeId}/subTicketType/{subTicketTypeId}")
    public ResponseEntity<Long> createSubscribe(@PathVariable("cafeId") Long cafeId,
                                             @PathVariable("subTicketTypeId") Long subTicketTypeId,
                                             @RequestBody @Valid SubscribeDto.SubscribeRequestDto request) {

        Long memberId = SecurityUtil.getCurrentMemberPk();
        Long result = consumerService.createSubscribe(request, cafeId, subTicketTypeId, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "CONS-B-02 :: 정기구독 내역 조회하기",
            notes = "정기 구독 내역을 조회합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/subscribe")
    public ResponseEntity<SubscribeDto.SubscribeListResponseDto> getSubscribe() {

        Long memberId = SecurityUtil.getCurrentMemberPk();
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getSubscribe(memberId));
    }

    @ApiOperation(value = "CONS-B-03 :: 정기구독 해지하기",
            notes = "정기 구독을 해지합니다. subscribeId에는 해지할 구독권 PK를 넣어서 요청합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @DeleteMapping("/subscribe/{subscribeId}")
    public ResponseEntity<?> cancelSubscribe(@PathVariable("subscribeId") Long subscribeId){

        Long memberId = SecurityUtil.getCurrentMemberPk();
        consumerService.cancelSubscribe(memberId, subscribeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO : 정기구독 결제
}
