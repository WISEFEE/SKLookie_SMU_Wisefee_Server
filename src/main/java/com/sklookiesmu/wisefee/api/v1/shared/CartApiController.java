package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.domain.Cart;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.service.shared.CartServiceImpl;
import com.sklookiesmu.wisefee.service.shared.MemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags= "Cart API")
@RestController
@RequiredArgsConstructor
public class CartApiController {
    private final MemberServiceImpl memberService;
    private final ModelMapper modelMapper;
    private final CartServiceImpl cartService;

    @PostMapping("/api/v1/addCart/")
    public ResponseEntity<Long> addCart(
            @ApiParam(name = "회원 PK 입력.")
            @RequestBody CartRequestDto cartRequestDto
    ){
        Cart cart = modelMapper.map(cartRequestDto, Cart.class);
        Long result = cartService.addCart(cart.getMember().getMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
