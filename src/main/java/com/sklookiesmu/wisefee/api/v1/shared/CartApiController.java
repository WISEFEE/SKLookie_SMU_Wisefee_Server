package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.error.ValidateMemberException;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.CartResponseDto;
import com.sklookiesmu.wisefee.service.shared.CartServiceImpl;
import com.sklookiesmu.wisefee.service.shared.MemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags= "Cart API")
@RestController
@RequiredArgsConstructor
public class CartApiController {
    private final MemberServiceImpl memberService;
    private final ModelMapper modelMapper;
    private final CartServiceImpl cartService;

//    @ApiOperation(
//            value = "장바구니 추가",
//            notes = "장바구니 추가 API입니다. <br>" +
//                    "회원번호 입력 시 회원용 장바구니가 생성됩니다."
//    )
//    @PostMapping("/api/v1/cart/{memberId}/")
//    public ResponseEntity<Long> addCart(@PathVariable("memberId") Long memberId) {
//
//        Long result = cartService.addCart(memberId);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "장바구니 조회",
            notes = "회원의 장바구니 List를 조회합니다."
    )
    @GetMapping("/api/v1/cart/{memberId}")
    public ResponseEntity<List<CartResponseDto.CartProductResponseDto>> findCartProduct(
            @PathVariable("memberId") Long memberId
    ){

        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new ValidateMemberException("invalid ID : The provided ID does not match your current logged-in ID"+memberId);

        List<CartResponseDto.CartProductResponseDto> result = cartService.findAllCartProduct(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "장바구니 상품 추가",
            notes = "카페, 장바구니, 상품, 선택옵션 아이디를 입력해 장바구니에 등록한다. +" +
                    "productOptChoices \"[]\" 에서 \"를 제거하고 [1,2,3] 과 같이 입력해야 한다."
    )
    @PostMapping("/api/v1/cart/{memberId}")
    public ResponseEntity<Long> addCartProduct(
            @ApiParam(value = "회원 PK")
            @PathVariable("memberId") Long memberId,
            @RequestBody CartRequestDto.CartProductRequestDto cartProductRequestDto
            ){
        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new ValidateMemberException("invalid ID : The provided ID does not match your current logged-in ID"+memberId);

        Long result = cartService.addCartProduct(memberId, cartProductRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}
