package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.service.seller.ProductStatusComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "SELL-D :: 주문 상태 변경 API")
@RequestMapping("/api/v1/seller/orders")
@RequiredArgsConstructor
public class ProductStatusController {

    private final ProductStatusComponent orderStatusComponent;

    @ApiOperation(
            value = "SELL-D-01 :: 주문 승인",
            notes = "매장에 주문이 들어오면 주문 상태를 주문 승인으로 합니다."
    )
    @PostMapping("/{orderId}/accept")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void acceptOrder(@PathVariable Long orderId) {
        orderStatusComponent.acceptOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-D-02 :: 주문 거절",
            notes = "매장에 주문이 들어오면 주문 상태를 주문 거절으로 합니다."
    )
    @PostMapping("/{orderId}/reject")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void rejectOrder(@PathVariable Long orderId) {
        orderStatusComponent.rejectOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-D-03 :: 주문 준비중",
            notes = "주문 상태를 주문 준비중으로 합니다."
    )
    @PostMapping("/{orderId}/prepare")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void preparingOrder(@PathVariable Long orderId) {
        orderStatusComponent.preparingOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-D-04 :: 주문 준비 완료",
            notes = "주문 상태를 주문 준비 완료로 합니다."
    )
    @PostMapping("/{orderId}/complete")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void completeOrder(@PathVariable Long orderId) {
        orderStatusComponent.completeOrder(orderId);
    }
}
