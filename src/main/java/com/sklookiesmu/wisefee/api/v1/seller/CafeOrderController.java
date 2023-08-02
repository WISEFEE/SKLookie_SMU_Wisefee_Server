package com.sklookiesmu.wisefee.api.v1.seller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.dto.seller.CreateOrderOptionRequestDto;
import com.sklookiesmu.wisefee.dto.seller.CreateOrderOptionResponseDto;
import com.sklookiesmu.wisefee.dto.seller.UpdateOrderOptionRequestDto;
import com.sklookiesmu.wisefee.dto.seller.UpdateOrderOptionResponseDto;
import com.sklookiesmu.wisefee.service.seller.CafeOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(tags = "매장 주문 API")
@RestController
@RequiredArgsConstructor
public class CafeOrderController {

    private final CafeOrderService cafeOrderService;

    @ApiOperation(value = "주문 옵션 추가")
    @PostMapping("/api/v1/seller/{cafeId}/orderOption")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderOptionResponseDto createOrderOption(@PathVariable("cafeId") Long cafeId,
                                                          @RequestBody @Valid CreateOrderOptionRequestDto requestDto) {
        Long orderOptionId = cafeOrderService.addOrderOption(cafeId, requestDto);

        return new CreateOrderOptionResponseDto(orderOptionId);
    }


    @ApiOperation(value = "주문 옵션 수정")
    @PutMapping("/api/v1/seller/{cafeId}/orderOption/{orderOptionId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public UpdateOrderOptionResponseDto updateOrderOption(@PathVariable("cafeId") Long cafeId,
                                                          @PathVariable("orderOptionId") Long orderOptionId,
                                                          @RequestBody @Valid UpdateOrderOptionRequestDto requestDto) {
        cafeOrderService.updateOrderOption(cafeId, orderOptionId, requestDto);
        OrderOption findOrderOption = cafeOrderService.findOrderOption(orderOptionId);

        return new UpdateOrderOptionResponseDto(findOrderOption.getOrderOptionId(), findOrderOption.getOrderOptionName(), findOrderOption.getOrderOptionPrice());
    }


    @ApiOperation(value = "주문 옵션 삭제")
    @DeleteMapping("/api/v1/seller/{cafeId}/orderOption/{orderOptionId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteOrderOption(@PathVariable("cafeId") Long cafeId,
                                  @PathVariable("orderOptionId") Long orderOptionId) {
        cafeOrderService.deleteOrderOption(cafeId, orderOptionId);
    }
}
