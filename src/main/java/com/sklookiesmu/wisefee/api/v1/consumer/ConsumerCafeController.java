package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import com.sklookiesmu.wisefee.service.consumer.ConsumerCafeServiceImpl;
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

@Api(tags = "고객 -> 매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class ConsumerCafeController {

    private final ConsumerCafeServiceImpl consumerService;

    @ApiOperation(value = "카페 리스트 조회",
    notes = "카페 목록을 조회하는 API입니다. 카페 이름순으로 정렬되어있습니다. <br><br>" +
            "pageSize = 한 페이지에서 나타내는 카페 수 <br>" +
            "pageNumber = 페이지 번호 (0번부터 시작) <br>" +
            "offset = 해당 페이지에 나타낼 시작 위치 <br>" +
            "paged = true <br>" +
            "unpaged = false <br>" +
            "sort.sorted = true <br>" +
            "sort.unsorted = false <br><br>" +
            "하단의 파라미터를 설정하지 않고 조회하면 전체 목록이 출력됩니다. " +
            "POSTMAN으로 실행 시 URL에 쿼리파라미터로 <br>" +
            "localhost:8080/api/v1/consumer/cafe?size=2 이런 식으로 요청하면 2개의 카페만 볼 수 있도록 되는데,, " +
            "스웨거에는 왜 적용이 안되는건지 잘 모르겠네요...ㅜㅜㅜ ")
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/cafe")
    public ResponseEntity<CafeDto.CafeListResponseDto> getCafeList(@PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeList(pageable));
    }

    @ApiOperation(value = "카페 음료 리스트 조회",
                    notes = "카페 ID(PK)로 카페를 선택한 후 해당하는 카페에 등록된 음료 목록들을 조회하는 API입니다. <br>" +
                            "제품 ID(PK), 제품명, 제품 가격, 제품 정보, 제품 옵션을 조회할 수 있습니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{cafeId}/product")
    public ResponseEntity<ProductDto.ProductListResponseDto> getProductList(@ApiParam(value = "카페 ID", required = true)
                                                                                @PathVariable("cafeId") Long cafeId) {
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getProductList(cafeId));
    }

    @ApiOperation(value = "매장 정보 조회",
            notes = "카페 ID(PK)로 카페를 선택한 후 해당하는 카페정보를 조회 API입니다. <br>" +
                    "매장ID(PK), 매장명, 매장설명, 매장연락처를 조회할 수 있습니다.")
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDto.CafeResponseDto> getCafeInfo(@ApiParam(value = "카페 ID", required = true)
                                                                   @PathVariable("cafeId") Long cafeId){
        return ResponseEntity.status(HttpStatus.OK).body(consumerService.getCafeInfo(cafeId));
    }

}
