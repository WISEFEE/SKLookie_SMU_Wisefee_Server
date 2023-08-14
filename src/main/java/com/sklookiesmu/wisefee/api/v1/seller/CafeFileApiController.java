package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.seller.EditCafeImageFileRequestDto;
import com.sklookiesmu.wisefee.service.seller.CafeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "SELL-G :: 매장 사진 API")
@RestController
@RequestMapping("/api/v1/seller/file")
@RequiredArgsConstructor
public class CafeFileApiController {
    private final CafeFileService cafeFileService;

    @ApiOperation(
            value = "SELL-G-01 :: 매장 사진 추가",
            notes = "매장의 사진을 추가합니다. 매장은 여러장의 사진을 가질 수 있습니다. <br>" +
                    "입력으로 등록할 이미지(파일)의 ID와, 등록 대상 매장 ID를 받습니다. <br>" +
                    "따라서 해당 API를 N번 사용하면, 이미지를 N개 등록할 수 있습니다. <br>" +
                    "단 매장이 가질 수 있는 매장사진의 최대 개수는 10개로 제한합니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @PostMapping("/cafe/{cafeId}")
    public ResponseEntity<Long> addCafeImage(@ApiParam(value = "카페 PK", required = true)
                                                 @PathVariable("cafeId") Long cafeId,
                                           @RequestBody @Valid EditCafeImageFileRequestDto requestDto){
        Long fileId = requestDto.getFileId();
        Long result = this.cafeFileService.addCafeImage(cafeId, fileId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @ApiOperation(
            value = "SELL-G-02 :: 매장 사진 삭제",
            notes = "이미 등록되어 있는 매장의 사진을 삭제합니다. <br>" +
                    "기존에 등록했던 파일 ID와 해당 매장의 ID를 파라미터로 받습니다. <br>" +
                    "요청 성공 시, 매장은 더이상 해당 이미지를 사용하지 않습니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @DeleteMapping("/cafe/{cafeId}")
    public ResponseEntity<Long> removeCafeImage(@ApiParam(value = "카페 PK", required = true)
                                                    @PathVariable("cafeId") Long cafeId,
                                           @RequestBody @Valid EditCafeImageFileRequestDto requestDto){
        Long fileId = requestDto.getFileId();
        Long result = this.cafeFileService.removeCafeImage(cafeId, fileId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }




}