package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.CafeProductDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerController {

    private final ConsumerServiceImpl consumerService;

    @ApiOperation(value = "카페 리스트 조회")
    @GetMapping("/cafe")
    public ResponseEntity<CafeDto.CafeListResponseDto> getCafeList(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeList(pageable));
    }

    @ApiOperation(value = "카페 음료 리스트 조회")
    @GetMapping("/{cafeId}/product")
    public ResponseEntity<CafeProductDto.CafeProductListResponseDto> getProductList(@PathVariable("cafeId") Long cafeId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getProductList(cafeId));
    }

    @ApiOperation(value = "매장 정보 조회")
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDto.CafeResponseDto> getCafeInfo(@PathVariable("cafeId") Long cafeId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeInfo(cafeId));
    }

    @ApiOperation(value = "주문 옵션 정보 조회")
    @GetMapping("/{cafeId}/order/{orderId}")
    public ResponseEntity<OrderOptionDto.OrderOptionResponseDto> getOrderOption(@PathVariable("cafeId") Long cafeId,
                                                         @PathVariable("orderId") Long orderId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getOrderOptionInfo(cafeId));
    }
}
