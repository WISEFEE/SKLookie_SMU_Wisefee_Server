package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Cart;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.CartRepository;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Override
    public Long addCart(Long memberId, Cart cart) {
        Member member = memberRepository.find(memberId);
        cart.getMember().setMemberId(memberId);
        cartRepository.createCart(cart);
        return cart.getCartId();
    }

    @Override
    public List<Cart> findAllCart(Long memberId, String order) {
        List<Cart> carts = cartRepository.findAllCartByMember(memberId, order);

        return carts;
    }
}
