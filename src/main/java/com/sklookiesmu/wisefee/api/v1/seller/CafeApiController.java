package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.service.seller.CafeOrderService;
import com.sklookiesmu.wisefee.service.seller.CafeProductService;
import com.sklookiesmu.wisefee.service.seller.CafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "SELL-A :: 매장 편집 API")
@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;
    private final CafeProductService cafeProductService;
    private final CafeOrderService cafeOrderService;

    @ApiOperation(
            value = "SELL-A-01 :: 매장 등록",
            notes = "매장 등록 API입니다. <br>" +
                    "매장 전화번호, 매장 설명, 매장명을 입력하여 보내면, 생성된 매장의 ID값을 반환합니다."
    )
    @PostMapping("/api/v1/seller/cafe")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCafeResponseDto createCafe(@RequestBody @Valid CreateCafeRequestDto requestDto) {

        Long pk = SecurityUtil.getCurrentMemberPk();

        if (pk == null) {
            throw new RuntimeException("현재 인증된 회원 정보를 가져올 수 없습니다.");
        }

        Cafe cafe = new Cafe();
        cafe.setTitle(requestDto.getTitle());
        cafe.setContent(requestDto.getContent());
        cafe.setCafePhone(requestDto.getCafePhone());

        Long addrId = requestDto.getAddrId();
        Long id = cafeService.create(cafe, addrId);
        return new CreateCafeResponseDto(id);
    }


    @ApiOperation(
            value = "SELL-A-02 :: 매장 정보 수정",
            notes = "매장 정보 수정 API입니다. <br>" +
                    "등록했던 매장 전화번호, 매장 설명, 매장명 중 수정하고 싶은 값을 보내면," +
                    "매장 ID, 매장 전화번호, 매장 설명, 매장명을 반환합니다."
    )
    @PutMapping("/api/v1/seller/cafe/{cafeId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public UpdateCafeResponseDto updateCafe(@PathVariable("cafeId") Long cafeId,
                                            @RequestBody @Valid UpdateCafeRequestDto requestDto) {
        cafeService.update(cafeId, requestDto);
        Cafe findCafe = cafeService.findCafe(cafeId);

        return new UpdateCafeResponseDto(findCafe.getCafeId(), findCafe.getTitle(), findCafe.getContent(), findCafe.getCafePhone());
    }


    @ApiOperation(
            value = "SELL-A-03 :: 매장 삭제",
            notes = "매장 삭제 API입니다. <br>" +
                    "엔티티를 직접 삭제 하지 않고, 소프트 삭제를 하여 cafe 테이블의 deleted_at의 값을 null에서 삭제 시점으로 바꾸게 됩니다." +
                    "후에 카페가 이미 삭제가 되었더라도 내역을 조회가 가능합니다."
    )
    @DeleteMapping("/api/v1/seller/cafe/{cafeId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteCafe(@PathVariable("cafeId") Long cafeId) {
        cafeService.delete(cafeId);
    }


    @ApiOperation(
            value = "SELL-A-04 :: 매장 정보 조회",
            notes = "매장 정보, 상품 리스트, 주문 옵션 정보가 함께 조회되는 API입니다."
    )
    @GetMapping("/api/v1/seller/cafe/{cafeId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public CafeDetailsDto getCafeDetails(@PathVariable("cafeId") Long cafeId) {
        Cafe findCafe = cafeService.findCafe(cafeId);
        List<Product> productList = cafeProductService.getProductsByCafeId(cafeId);
        List<OrderOption> orderOptionList = cafeOrderService.getOrderOptionsByCafeId(cafeId);

        CafeDetailsDto cafeDetailsDto = new CafeDetailsDto();
        cafeDetailsDto.setCafeId(findCafe.getCafeId());
        cafeDetailsDto.setTitle(findCafe.getTitle());
        cafeDetailsDto.setContent(findCafe.getContent());
        cafeDetailsDto.setCafePhone(findCafe.getCafePhone());
        cafeDetailsDto.setProducts(productList);
        cafeDetailsDto.setOrderOptions(orderOptionList);

        return cafeDetailsDto;
    }


}
