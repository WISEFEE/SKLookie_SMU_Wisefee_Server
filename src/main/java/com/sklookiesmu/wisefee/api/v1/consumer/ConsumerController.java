package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
import com.sklookiesmu.wisefee.dto.consumer.SubscribeDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "고객 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerController {

    private final ConsumerServiceImpl consumerService;

    @ApiOperation(value = "정기 구독하기")
    @PostMapping("/{cafeId}/subscribe/{subTicketTypeId}")
    public ResponseEntity<?> createSubscribe(@PathVariable("cafeId") Long cafeId,
                                             @PathVariable("subTicketTypeId") Long subTicketTypeId,
                                             @RequestBody SubscribeDto.SubscribeRequestDto request) {


        consumerService.createSubscribe(request, cafeId, subTicketTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "정기구독 내역 조회하기")
    @GetMapping("/{memberId}/subscribe")
    public ResponseEntity<SubscribeDto.SubscribeListResponseDto> getSubscribe(@PathVariable("memberId") Long memberId) {

        // 유저 검증 필요
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getSubscribe(memberId));
    }

    // TODO : 정기구독 결제
}
