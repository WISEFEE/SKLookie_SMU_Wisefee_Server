package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "CONS-C :: 매장 정보(주문관련) API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerOrderController {

    private final ConsumerOrderServiceImpl consumerOrderService;
    @ApiOperation(value = "CONS-C-01 :: 주문 옵션 정보 조회")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{cafeId}/orderOption")
    public ResponseEntity<OrderOptionDto.OrderOptionListResponseDto> getOrderOption(@PathVariable("cafeId") Long cafeId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.getOrderOptionInfo(cafeId));
    }


    @ApiOperation(value = "CONS-C-02 :: 주문하기",
                notes = "- 아이스 아메리카노(PRODUCT : ID=13)\n" +
                        "    - 사이즈 선택(PRODUCT_OPTION : ID=1)\n" +
                        "        - 벤티(+1000)  (PRODUCT_OPT_CHOICE : ID=101)\n" +
                        "        - 그란데(+2000)  (PRODUCT_OPT_CHOICE : ID=102)\n" +
                        "    - 휘핑크림 추가 (PRODUCT_OPTION : ID=2)\n" +
                        "        - 추가 (+500)  (PRODUCT_OPT_CHOICE : ID=103)\n\n" +
                        "- orderOption는 주문 옵션 - 빨대 추가 여부, 고무컵홀더 사용여부 등등..")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @PostMapping("/{cafeId}/order")
    public ResponseEntity<Long> createOrder(@PathVariable("cafeId") Long cafeId,
                                            @Valid @RequestBody OrderDto.OrderRequestDto orderRequest){
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.createOrder(cafeId, orderRequest));
    }

    @ApiOperation(value = "CONS-C-03 :: 주문내역 조회하기",
                    notes = "카페 PK와 주문 PK를 입력 시 고객이 주문한 내역을 조회할 수 있습니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{cafeId}/order/{orderId}")
    public ResponseEntity<OrderDto.OrderResponseDto> getOrderHistory(@PathVariable("cafeId") Long cafeId,
                                                                     @PathVariable("orderId") Long orderId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.getOrderHistory(cafeId, orderId));
    }

}
