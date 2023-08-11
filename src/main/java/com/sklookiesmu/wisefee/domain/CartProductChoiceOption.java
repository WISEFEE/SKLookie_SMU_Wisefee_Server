package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CART_PRODUCT_CHOICE_OPTION")
public class CartProductChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_PRODUCT_CHOICE_OPTION_ID")
    private Long cartProductChoiceOptionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_PRODUCT")
    private CartProduct cartProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_OPT_CHOICE")
    private ProductOptChoice productOptChoice;

    public CartProductChoiceOption(CartProduct cartProduct, ProductOptChoice productOptChoice) {
        this.cartProduct = cartProduct;
        this.productOptChoice = productOptChoice;
    }

    public CartProductChoiceOption() {

    }
}
