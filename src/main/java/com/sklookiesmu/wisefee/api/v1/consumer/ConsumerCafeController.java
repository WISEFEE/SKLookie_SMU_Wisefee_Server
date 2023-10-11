package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerCafeServiceImpl;
import com.sklookiesmu.wisefee.service.consumer.ConsumerOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "CONS-A :: 매장 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerCafeController {

    private final ConsumerCafeServiceImpl consumerService;

    private final ConsumerOrderService consumerOrderService;

    @ApiOperation(value = "CONS-A-01 :: 카페 리스트 조회",
    notes = "카페 목록을 조회하는 API입니다. 카페 이름순으로 정렬되어있습니다. <br><br>" +
            "pageSize = 한 페이지에서 나타내는 카페 수 <br>" +
            "page = 페이지 번호 (0번부터 시작) <br>" +
            "하단의 파라미터를 설정하지 않고 조회하면 전체 목록이 출력됩니다. " )

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/cafe")
    public ResponseEntity<CafeDto.CafeListResponseDto> getCafeList(@PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeList(pageable));
    }

    @ApiOperation(value = "CONS-A-02 :: 카페 음료 리스트 조회",
                    notes = "카페 ID(PK)로 카페를 선택한 후 해당하는 카페에 등록된 음료 목록들을 조회하는 API입니다. <br>" +
                            "제품 ID(PK), 제품명, 제품 가격, 제품 정보, 제품 사진 ID, 제품 옵션을 조회할 수 있습니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/cafe/{cafeId}/product")
    public ResponseEntity<ProductDto.ProductListResponseDto> getProductList(@ApiParam(value = "카페 ID", required = true)
                                                                                @PathVariable("cafeId") Long cafeId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getProductList(cafeId));
    }

    @ApiOperation(value = "CONS-A-03 :: 매장 정보 조회",
            notes = "카페 ID(PK)로 카페를 선택한 후 해당하는 카페정보를 조회 API입니다. <br>" +
                    "매장ID(PK), 매장명, 매장설명, 매장연락처, 매장 사진 ID, 매장 주소를 조회할 수 있습니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<CafeDto.CafeResponseDto> getCafeInfo(@ApiParam(value = "카페 ID", required = true)
                                                                   @PathVariable("cafeId") Long cafeId){

        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeInfo(cafeId));
    }


    @ApiOperation(value = "CONS-A-04 :: 주문 옵션 정보 조회")
    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @GetMapping("/cafe/{cafeId}/orderOption")
    public ResponseEntity<OrderOptionDto.OrderOptionListResponseDto> getOrderOption(@PathVariable("cafeId") Long cafeId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerOrderService.getOrderOptionInfo(cafeId));
    }

}
