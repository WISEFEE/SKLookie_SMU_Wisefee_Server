package com.sklookiesmu.wisefee.api.v1.consumer;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.exception.global.AuthForbiddenException;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.CartResponseDto;
import com.sklookiesmu.wisefee.service.shared.CartServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "CONS-D :: 장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consumer")
public class CartApiController {
    private final CartServiceImpl cartService;


    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-01 :: 장바구니 id 조회",
            notes = "회원 id를 입력해 회원의 장바구니 id를 조회합니다."
    )
    @GetMapping("/cart/find/{memberId}")
    public ResponseEntity<Long> findCartId(
            @ApiParam("회원 아이디")
            @PathVariable("memberId") Long memberId
    ) {
        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AuthForbiddenException("현재 로그인한 회원의 권한이 아닙니다.");

        Long result = cartService.findCartId(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-02 :: 장바구니 금액조회",
            notes = "장바구니 총 금액 조회 API입니다. <br>" +
                    "회원 아이디 입력 시 현재 회원의 장바구니에 담긴 상품들의 총 가격을 조회합니다."
    )
    @GetMapping("/cart/price/{memberId}")
    public ResponseEntity<Long> findCartTotalPrice(
            @ApiParam(value = "회원 ID")
            @PathVariable("memberId") Long memberId
    ) {
        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AuthForbiddenException("현재 로그인한 회원의 권한이 아닙니다.");

        Long result = cartService.calculateCart(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-03 :: 장바구니 금액조회 (구독권 할인 적용)",
            notes = "구독권의 할인률을 적용한 장바구니 총 금액 조회 API입니다. <br>" +
                    "회원 아이디 입력 시 현재 회원의 장바구니에 담긴 상품들의 총 가격을 조회합니다."
    )
    @GetMapping("/cart/price/sub-ticket/{memberId}")
    public ResponseEntity<Long> findCartTotalPriceWithSubTicket(
            @ApiParam(value = "회원 PK")
            @PathVariable("memberId") Long memberId,
            @ApiParam(value = "적용할 구독 아이디")
            @RequestParam("subscribeId") Long subscribeId
    ) {
        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AuthForbiddenException("현재 로그인한 회원의 권한이 아닙니다.");

        Long result = cartService.calculateCartWithSubTicket(memberId, subscribeId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-04 :: 장바구니 조회",
            notes = "회원의 장바구니 List를 조회합니다."
    )
    @GetMapping("/cart/{memberId}")
    public ResponseEntity<List<CartResponseDto.CartProductResponseDto>> findCartProduct(
            @PathVariable("memberId") Long memberId
    ){

        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AuthForbiddenException("현재 로그인한 회원의 권한이 아닙니다.");

        List<CartResponseDto.CartProductResponseDto> result = cartService.findAllCartProduct(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-05 :: 장바구니 상품 추가",
            notes = "카페, 장바구니, 상품, 선택옵션 아이디를 입력해 장바구니에 등록한다. +" +
                    "productOptChoices \"[]\" 에서 \"를 제거하고 [1,2,3] 과 같이 입력해야 한다."
    )
    @PostMapping("/cart/{memberId}")
    public ResponseEntity<Long> addCartProduct(
            @ApiParam(value = "회원 PK")
            @PathVariable("memberId") Long memberId,
            @RequestBody CartRequestDto.CartProductRequestDto cartProductRequestDto
            ){
        if(!(memberId.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AuthForbiddenException("현재 로그인한 회원의 권한이 아닙니다.");

        Long result = cartService.addCartProduct(memberId, cartProductRequestDto);


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-06 :: 상품옵션 수정",
            notes = "수정할 장바구니 상품 아이디와 수정할 개수를 입력합니다." +
                    "<br> 또한, 상품 개수가 0 이하가 될 시 자동으로 상품을 삭제합니다."
    )
    @PutMapping("/cart/update/{cartProductId}")
    public ResponseEntity<Long> updateCartProduct(
            @ApiParam("장바구니 상품 아이디")
            @PathVariable("cartProductId") Long cartProductId,
            @Valid @RequestBody CartRequestDto.CartProductUpdateRequestDTO dto
            ){
        Long result = cartService.updateCartProduct(cartProductId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PreAuthorize(AuthConstant.AUTH_ROLE_CONSUMER)
    @ApiOperation(
            value = "CONS-D-07 :: 장바구니 상품 삭제",
            notes = "장바구니 상품을 삭제합니다."
    )
    @DeleteMapping("/cart/delete/{cartProductId}")
    public ResponseEntity<Long> deleteCartProduct(
            @ApiParam(value = "장바구니 상품 PK")
            @PathVariable("cartProductId") Long cartProductId
    ) {

        Long result = cartService.deleteCartProduct(cartProductId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
