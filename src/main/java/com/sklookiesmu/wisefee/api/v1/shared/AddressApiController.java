package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.address.AddressRequestDto;
import com.sklookiesmu.wisefee.dto.shared.address.AddressResponseDto;
import com.sklookiesmu.wisefee.service.shared.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "COMM-A :: 주소 API")
@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressApiController {
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @ApiOperation(
            value = "COMM-A-01 :: 주소 추가",
            notes = "주소를 추가하는 API입니다. 회원의 선호지역, 매장 등록 시 주소 ID(PK)를 함께 Request Payload로 넘겨 주어야 합니다." +
                    "따라서 그 전에 해당 API를 통해 먼저 주소를 등록해 준 이후 반환받은 주소 ID를 매장 등록, 선호지역 등록 등에서 사용하시면 됩니다." +
                    "해당 API의 응답으로는 등록 성공한 주소의 ID(PK)값을 반환해줍니다." +
                    "<br><br>" +
                    "해당 API는 KaKao 주소 OpenAPI를 기반으로 만들어졌습니다. OpenAPI를 이용하여 얻어낸 응답값들을 기반으로 요청으로 보내주시면 됩니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping("")
    public ResponseEntity<Long> addAddress(@Valid @RequestBody AddressRequestDto address){
        Address entity = modelMapper.map(address, Address.class);
        Long id = addressService.insert(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    @ApiOperation(
            value = "COMM-A-02 :: 주소 ID로 검색",
            notes = "주소를 ID(PK)로 검색하는 API입니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findAddressById(
            @ApiParam(value = "주소 ID", required = true)
            @PathVariable("id") Long id
    ){
        Address address = addressService.selectById(id);
        if(address == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        AddressResponseDto result = modelMapper.map(address, AddressResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}