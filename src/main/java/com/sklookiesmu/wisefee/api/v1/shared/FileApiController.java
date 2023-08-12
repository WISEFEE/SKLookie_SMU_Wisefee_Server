package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Address;
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

@Api(tags = "COMM-E :: 파일 API")
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileApiController {
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @ApiOperation(
            value = "COMM-A-01 :: 파일 추가"
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping("")
    public ResponseEntity<Long> addAddress(@Valid @RequestBody AddressRequestDto address){
        Address entity = modelMapper.map(address, Address.class);
        Long id = addressService.insert(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    @ApiOperation(
            value = "COMM-A-02 :: 파일 ID로 검색"
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findAddressById(
            @ApiParam(value = "파일 ID", required = true)
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