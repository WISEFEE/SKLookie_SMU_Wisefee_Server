package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.OrdersDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersInfoDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerOrderCheckingService;
import com.sklookiesmu.wisefee.service.consumer.ConsumerOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "CONS-F :: 주문조회관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerOrderCheckingController {
    private final ConsumerOrderCheckingService consumerOrderCheckingService;

    @ApiOperation(value = "CONS-F-01 :: 결제된 미완의 주문 내역 조회하기",
            notes = "결제된 미완의 주문 내역을 조회합니다. memberId에는 조회할 사용자의 PK를 넣어서 요청합니다.<br><br>"+
    "현재 구독중인 구독권기준으로 연관된 주문중 DONE(완료)상태가 아닌 모든 주문을 반환합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{memberId}/paidOrderHistory")
    public ResponseEntity<OrdersInfoDto> getPaidOrdersHistory(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderCheckingService.getPaidOrdersHistory(memberId));
    }
}
