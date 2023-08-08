package com.sklookiesmu.wisefee.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sklookiesmu.wisefee.domain.Cart;
import com.sklookiesmu.wisefee.domain.QCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    /**
     * Cart 엔티티를 받아와 DB에 추가시킨다.
     * @param [cart 추가할 Cart 엔티티]
     */
    public void createCart(Cart cart) {
        em.persist(cart);
    }

    /**
     * 특정 회원의 장바구니를 조회한다.
     * @param [memberId 장바구니를 조회할 회원]
     * @param  [order 정렬 순서]
     * @return [회원의 장바구니 조회]
     */
    public List<Cart> findAllCartByMember(Long memberId, String order) {
        QCart cart = QCart.cart;
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        BooleanExpression condition = cart.member.memberId.eq(memberId);
        BooleanExpression notNullDeletedAt = cart.deletedAt.isNotNull();
        BooleanExpression combineCondition = condition.and(notNullDeletedAt);

        List<Cart> result = jpaQueryFactory.select(cart).where(combineCondition).fetch();

        if(order.equals("desc")) {
            result = jpaQueryFactory.select(cart).where(combineCondition).
                    orderBy(cart.cartId.desc()).fetch();
        }

        return result;
    }




    




}
