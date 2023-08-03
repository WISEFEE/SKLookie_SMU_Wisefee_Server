package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerCafeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "고객 -> 매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerCafeController {

    private final ConsumerCafeServiceImpl consumerService;

    @ApiOperation(value = "카페 리스트 조회")
    @GetMapping("/cafe")
    public ResponseEntity<CafeDto.CafeListResponseDto> getCafeList(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeList(pageable));
    }

    @ApiOperation(value = "카페 음료 리스트 조회")
    @GetMapping("/{cafeId}/product")
    public ResponseEntity<ProductDto.ProductListResponseDto> getProductList(@PathVariable("cafeId") Long cafeId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getProductList(cafeId));
    }

    @ApiOperation(value = "매장 정보 조회")
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDto.CafeResponseDto> getCafeInfo(@PathVariable("cafeId") Long cafeId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeInfo(cafeId));
    }

}
