package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.CartProduct;
import com.sklookiesmu.wisefee.dto.shared.member.CartRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.CartResponseDto;

import java.util.List;

public interface CartService {

    /**
     * [장바구니 추가 서비스]
     * @param [memberId Cart 주인]
     * @return [추가한 장바구니 PK]
     */
    Long addCart(Long memberId, boolean flagDelete);

    /**
     * [장바구니 상품 추가]
     * @param [cartId 추가할 cartId]
     * @param [CartProductRequestDto 추가할 상품 DTO]
     * @return [추가된 cartProduct PK]
     */
     Long[] addCartProduct(Long memberId, CartRequestDto.CartProductRequestDto cartRequestDto);

    /**
     * [회원 장바구니 조회]
     * @param [memberId 회원 PK]
     * @return [장바구니 List]
     */
    List<CartResponseDto.CartProductResponseDto> findAllCartProduct(Long memberId);

    /**
     * [장바구니 상품 업데이트]
     * @param [cartProductId 수정할 장바구니상품 PK]
     * @param [cartProductUpdateRequestDto 수정할 DTO]
     * @return [수정한 CartProduct PK]
     */
    Long updateCartProduct(Long cartProductId,CartRequestDto.CartProductUpdateRequestDTO cartProductUpdateRequestDto);

    /**
     * [장바구니 상품 삭제]
     * @param [cartProductId 삭제할 장바구니 PK]
     */
    Long deleteCartProduct(Long cartProductId);

    /**
     * [장바구니 총 금액 계산]
     * @param [cartId 계산할 장바구니 아이디]
     * @return [계산금액, 계산 로직 -> productPrice * productQuantity + productOptChoicePrice * productQuantity ]
     */
    Long calculateCart(Long cartId);
}
