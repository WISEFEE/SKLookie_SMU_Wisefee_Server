package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.OrderDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.PaymentDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "CONS-C :: 주문관련 API")
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
                notes = "본 API는 카페 음료를 주문하는 기능입니다. 주문 시 구독 번호를 함께 넘겨주어야 하며, 구독권 조건에 따라 최소인원/최대인원의 조건에 따라 음료 개수를 맞추어 주문해야 합니다.\n\n" +
                        "입력 데이터:\n" +
                        "```json\n" +
                        "{\n" +
                        "  \"주문 옵션 ID \"    : \"orderOptionId\",                // 주문 옵션 Id은 필수 입력값이 아닙니다.   ex). 빨대추가\n" +
                        "  \"제품 ID\"         : \"productId\",                    // 주문할 제품의 ID를 입력합니다.         ex). 아이스 아메리카노\n" +
                        "  \"제품 옵션 ID\"     : \"orderProductOptionId\",         // 주문할 제품의 옵션 ID를 입력합니다.     ex). 사이즈 선택\n" +
                        "  \"제품 옵션 선택지 ID\": \"orderProductOptionId\",         // 주문할 제품의 옵션 선택 ID를 입력합니다. ex). 벤티\n" +
                        "}\n" +
                        "```\n\n<hr>"+
                        "- orderOption는 주문 옵션을 입력합니다. - 빨대 추가 여부, 고무컵홀더 사용여부 등등.., 만약 주문 옵션을 선택하지 않는다면 빈 리스트 []을 넘겨주세요\n\n"+
                        "- orderProduct는 주문할 제품을 입력합니다. - 만약 상품 옵션을 선택하지 않는다면 빈 리스트 []을 넘겨주세요\n" +
                        "- 만약 같은 상품을 3개 주문하려면 orderProduct 리스트에 똑같은 Element를 넣어서 보내면 됩니다."+
                        "   - 아이스 아메리카노(PRODUCT : ID=13)\n" +
                        "       - 사이즈 선택(PRODUCT_OPTION : ID=1)\n" +
                        "           - 벤티(+1000)  (PRODUCT_OPT_CHOICE : ID=101)\n" +
                        "           - 그란데(+2000)  (PRODUCT_OPT_CHOICE : ID=102)\n" +
                        "       - 휘핑크림 추가 (PRODUCT_OPTION : ID=2)\n" +
                        "           - 추가 (+500)  (PRODUCT_OPT_CHOICE : ID=103)")
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

    @ApiOperation(value = "CONS-C-04 :: 주문 금액 조회하기",
            notes = "주문한 내역의 금액을 조회합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/{cafeId}/order/{orderId}/payment")
    public ResponseEntity<PaymentDto.PaymentResponseDto> getPayment(@PathVariable("cafeId") Long cafeId,
                                                                    @PathVariable("orderId") Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.getPayment(cafeId, orderId));
    }

     /* @ApiOperation(value = "CONS-C-05 :: 주문 금액 생성하기",
            notes = "주문한 내역의 총금액을 생성합니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @PostMapping("/{cafeId}/order/{orderId}/payment")
    public ResponseEntity<Long> createPayment(@PathVariable("cafeId") Long cafeId,
                                              @PathVariable("orderId") Long orderId,
                                              @Valid @RequestBody PaymentDto.PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.createPaymentMethod(cafeId, orderId, paymentRequestDto));
    }*/
}
