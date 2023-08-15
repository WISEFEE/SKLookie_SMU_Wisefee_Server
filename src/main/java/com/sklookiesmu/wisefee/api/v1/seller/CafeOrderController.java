package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.domain.Order;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.service.seller.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.service.seller.CafeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "SELL-B :: 매장 주문 옵션 편집 API")
@RestController
@RequiredArgsConstructor
public class CafeOrderController {

    private final CafeOrderService cafeOrderService;
    private final OrderService orderService;

    @ApiOperation(
            value = "SELL-B-01 :: 주문 옵션 추가",
            notes = "주문 옵션 추가하는 API입니다. <br>" +
                    "주문 옵션명과 가격을 입력하면, 주문 옵션 ID를 반환합니다."
    )
    @PostMapping("/api/v1/seller/{cafeId}/orderOption")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderOptionResponseDto createOrderOption(@PathVariable("cafeId") Long cafeId,
                                                          @RequestBody @Valid CreateOrderOptionRequestDto requestDto) {
        Long orderOptionId = cafeOrderService.addOrderOption(cafeId, requestDto);

        return new CreateOrderOptionResponseDto(orderOptionId);
    }


//    @ApiOperation(
//            value = "주문 옵션 수정",
//            notes = "주문 옵션 수정 API입니다. <br>" +
//                    "등록했던 주문 옵션명과 가격중 수정할 값을 입력합니다."
//    )
//    @PutMapping("/api/v1/seller/{cafeId}/orderOption/{orderOptionId}")
//    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
//    public UpdateOrderOptionResponseDto updateOrderOption(@PathVariable("cafeId") Long cafeId,
//                                                          @PathVariable("orderOptionId") Long orderOptionId,
//                                                          @RequestBody @Valid UpdateOrderOptionRequestDto requestDto) {
//        cafeOrderService.updateOrderOption(cafeId, orderOptionId, requestDto);
//        OrderOption findOrderOption = cafeOrderService.findOrderOption(orderOptionId);
//
//        return new UpdateOrderOptionResponseDto(findOrderOption.getOrderOptionId(), findOrderOption.getOrderOptionName(), findOrderOption.getOrderOptionPrice());
//    }


    @ApiOperation(
            value = "SELL-B-02 :: 주문 옵션 삭제",
            notes = "주문 옵션 삭제 API입니다. <br>" +
                    "엔티티를 직접 삭제하지 않고, deleted_at속성값을 삭제 시점으로 하여 소프트 삭제를 합니다."
    )
    @DeleteMapping("/api/v1/seller/{cafeId}/orderOption/{orderOptionId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteOrderOption(@PathVariable("cafeId") Long cafeId,
                                  @PathVariable("orderOptionId") Long orderOptionId) {
        cafeOrderService.deleteOrderOption(cafeId, orderOptionId);
    }


    @ApiOperation(
            value = "SELL-B-03 :: 매장에 들어온 주문 리스트",
            notes = "매장에 들어온 주문들의 리스트를 조회하는 API입니다."
    )
    @GetMapping("/api/v1/seller/{cafeId}/orderList")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public List<OrderListDto> getOrdersList(@PathVariable("cafeId") Long cafeId) {
        List<Order> orders = orderService.getOrdersByCafeId(cafeId);
        return orders.stream()
                .map(order -> OrderListDto.fromOrder(order))
                .collect(Collectors.toList());
    }


    @ApiOperation(
            value = "SELL-B-04 :: 주문 상세 정보",
            notes = "주문 상세 정보 API입니다. <br>" +
                    "들어온 주문의 상세 정보를 볼 수 있습니다."
    )
    @GetMapping("/api/v1/seller/orders/{orderId}/details")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public OrderDetailsDto getOrderDetails(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return OrderDetailsDto.fromOrder(order);
    }

}
