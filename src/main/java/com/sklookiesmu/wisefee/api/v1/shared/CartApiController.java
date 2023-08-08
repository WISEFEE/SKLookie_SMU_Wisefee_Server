package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.dto.shared.CartRequestDto;
import com.sklookiesmu.wisefee.service.shared.MemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value= "Cart API")
@RestController
@RequiredArgsConstructor
public class CartApiController {
    private final MemberServiceImpl memberService;
    private final ModelMapper modelMapper;

    @PostMapping("api/v1/addCart/{id}")
    public ResponseEntity<Long> addCart(
            @ApiParam(name = "회원 PK 입력.")
            @PathVariable("memberId") Long memberId,
            @ApiParam(name = "추가할 카페 ID")
            @RequestParam(value = "cafeId") Long cafeId,
            @Valid @RequestBody CartRequestDto cart
    ){
        return null;
    }

}
