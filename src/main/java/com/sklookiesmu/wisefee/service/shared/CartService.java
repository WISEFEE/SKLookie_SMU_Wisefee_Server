package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Cart;
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
    Long addCart(Long memberId);

    /**
     * [장바구니 상품 추가]
     * @param [cartId 추가할 cartId]
     * @param [productId 추가할 상품 PK]
     * @return [추가된 cartProduct PK]
     */
     Long addCartProduct(Long memberId, CartRequestDto.CartProductRequestDto cartRequestDto);

    /**
     * [cartId로 장바구니 상품 조회]
     * @param [cartId 조회할 장바구니 PK]
     * @return [조회한 장바구니 상품 리스트]
     */
    List<CartProduct> findCartProductByCart(Long cartId);

    /**
     * [회원 장바구니 조회]
     * @param [memberId 회원 PK]
     * @param [order 정렬 방법 => desc, asc]
     * @return [장바구니 List]
     */
    List<CartResponseDto.CartProductResponseDto> findAllCartProduct(Long memberId);
}
