package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.service.seller.CafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "매장 API")
@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;

    @ApiOperation(value = "매장 등록")
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


    @ApiOperation(value = "매장 정보 수정")
    @PutMapping("/api/v1/seller/cafe/{cafeId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public UpdateCafeResponseDto updateCafe(@PathVariable("cafeId") Long cafeId,
                                            @RequestBody @Valid UpdateCafeRequestDto requestDto) {
        cafeService.update(cafeId, requestDto);
        Cafe findCafe = cafeService.findCafe(cafeId);

        return new UpdateCafeResponseDto(findCafe.getCafeId(), findCafe.getTitle(), findCafe.getContent(), findCafe.getCafePhone());
    }


    @ApiOperation(value = "매장 삭제")
    @DeleteMapping("/api/v1/seller/cafe/{cafeId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteCafe(@PathVariable("cafeId") Long cafeId) {
        cafeService.delete(cafeId);
    }

}
