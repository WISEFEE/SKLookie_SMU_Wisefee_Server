package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.service.seller.ProductStatusComponent;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller/orders")
@RequiredArgsConstructor
public class ProductStatusController {

    private final ProductStatusComponent orderStatusComponent;

    @ApiOperation(
            value = "SELL-A-04 :: 매장 정보 조회",
            notes = "매장 정보, 상품 리스트, 주문 옵션 정보가 함께 조회되는 API입니다."
    )
    @PostMapping("/{orderId}/accept")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void acceptOrder(@PathVariable Long orderId) {
        orderStatusComponent.acceptOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-A-04 :: 매장 정보 조회",
            notes = "매장 정보, 상품 리스트, 주문 옵션 정보가 함께 조회되는 API입니다."
    )
    @PostMapping("/{orderId}/reject")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void rejectOrder(@PathVariable Long orderId) {
        orderStatusComponent.rejectOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-A-04 :: 매장 정보 조회",
            notes = "매장 정보, 상품 리스트, 주문 옵션 정보가 함께 조회되는 API입니다."
    )
    @PostMapping("/{orderId}/prepare")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void preparingOrder(@PathVariable Long orderId) {
        orderStatusComponent.preparingOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-A-04 :: 매장 정보 조회",
            notes = "매장 정보, 상품 리스트, 주문 옵션 정보가 함께 조회되는 API입니다."
    )
    @PostMapping("/{orderId}/complete")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void completeOrder(@PathVariable Long orderId) {
        orderStatusComponent.completeOrder(orderId);
    }
}
