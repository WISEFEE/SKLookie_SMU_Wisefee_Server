package com.sklookiesmu.wisefee.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sklookiesmu.wisefee.domain.Cart;
import com.sklookiesmu.wisefee.domain.CartProduct;
import com.sklookiesmu.wisefee.domain.QCart;
import com.sklookiesmu.wisefee.domain.QCartProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
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
    public Cart findCartByMember(Long memberId, String order) {
        QCart cart = QCart.cart;

        System.out.println("Repository : " + cart.member.toString());
        BooleanExpression condition = cart.member.memberId.eq(memberId);
        BooleanExpression notNullDeletedAt = cart.deletedAt.isNotNull();
        BooleanExpression combineCondition = condition.and(notNullDeletedAt);

        JPAQuery<Cart> query = jpaQueryFactory.select(cart).where(combineCondition);

        if(order.equals("desc")) {
            query.orderBy(cart.cartId.desc()).fetch();
        }

        return query.fetchOne();
    }

    /**
     * 장바구니를 조회한다.
     *
     * @param [cartId 조회할 장바구니 PK]
     * @return [조회한 장바구니]
     */
    public Optional<Cart> findCartByCartId(Long cartId) {
        Optional<Cart> result = Optional.ofNullable(em.find(Cart.class, cartId));

        return result;
    }


    /**
     * [cartProduct 추가, cartProduct는 장바구니<>상품 다대다 관계 연결 엔티티]
     * @param [cartProduct 추가할 cartProduct 엔티티]
     */
    public void createCartProduct(CartProduct cartProduct){
        em.persist(cartProduct);
    }


    /**
     * [cartId기준 cartProduct 조회]
     * @param [cartId 조회할 cartId]
     * @return [조회된 cartProduct 리스트]
     */
    public List<CartProduct> findCartProductByCartId(Long cartId){
        QCartProduct cartProduct = QCartProduct.cartProduct;
        List<CartProduct> result = jpaQueryFactory.selectFrom(cartProduct)
                .where(cartProduct.cart.cartId.eq(cartId))
                .fetch();
        return result;
    }

    /**
     * [productId기준 cartProduct 조회]
     * @param [productId 조회할 productId]
     * @return [조회된 cartProduct 리스트]
     */
    public List<CartProduct> findCartProductByProductId(Long productId){
        QCartProduct cartProduct = QCartProduct.cartProduct;
        List<CartProduct> result = jpaQueryFactory.selectFrom(cartProduct)
                .where(cartProduct.product.productId.eq(productId))
                .fetch();
        return result;
    }



}
