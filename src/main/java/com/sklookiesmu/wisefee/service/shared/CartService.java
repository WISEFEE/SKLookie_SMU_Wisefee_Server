package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Cart;

import java.util.List;

public interface CartService {

    /**
     * [장바구니 추가 서비스]
     * @param [memberId Cart 주인]
     * @param [cart 추가할 장바구니]
     * @return [추가한 장바구니 PK]
     */
    Long addCart(Long memberId, Cart cart);

    /**
     * [회원 장바구니 조회]
     * @param [memberId 회원 PK]
     * @param [order 정렬 방법 => desc, asc]
     * @return [장바구니 List]
     */
    List<Cart> findAllCart(Long memberId, String order);
}
