package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.OrdersDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerMyPageService;
import com.sklookiesmu.wisefee.service.consumer.ConsumerMyPageServiceImpl;
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

@Api(tags = "CONS-E :: 마이페이지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerMyPageController {

    private final ConsumerMyPageService consumerMyPageService;

    @ApiOperation(value = "CONS-E-01 :: 정기구독한 카페 내역 조회하기",
            notes = "정기 구독한 카페 내역을 조회합니다. memberId에는 조회할 사용자의 PK를 넣어서 요청합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{memberId}/subscribedCafes")
    public ResponseEntity<CafeDto.CafeListResponseDto> getSubscribedCafe(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerMyPageService.getSubscribedCafe(memberId));
    }

    @ApiOperation(value = "CONS-E-02 :: 전체 주문 내역 조회하기",
            notes = "주문 내역을 조회합니다. memberId에는 조회할 사용자의 PK를 넣어서 요청합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{memberId}/orderHistory")
    public ResponseEntity<OrdersDto> getAllOrderHistory(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerMyPageService.getAllOrderHistory(memberId));
    }
}
