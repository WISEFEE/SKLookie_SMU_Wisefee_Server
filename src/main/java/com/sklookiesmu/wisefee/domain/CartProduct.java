package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CART_PRODUCT")
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_PRODUCT_ID")
    private Long cartProductId;

    @Column(name = "CART_PRODUCT_QUANTITY")
    private Long productQuantity;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    /**
     * 생성일, 수정일 값 세팅
     */
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


//    @Column(name = "TUMBLER_QUANTITY")
//    private Long tumblerQuantity;

    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "cartProduct")
    private List<CartProductChoiceOption> cartProductChoiceOptions;

    /**w
     * 비즈니스 로직
     */
    public void addCartProduct(Long productQuantity, Cart cart, Product product){
        this.productQuantity = productQuantity;
        this.cart = cart;
        this.product = product;
    }

    public Long updateQuantity(Long updateQuantity) {
        this.productQuantity += updateQuantity;
        return 1L;
    }


}
