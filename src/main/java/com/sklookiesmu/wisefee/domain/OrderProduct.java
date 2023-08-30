package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_PRODUCT")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT_ID")
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PAYMENT_ID")
//    private Order order_payment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToMany(mappedBy = "orderProduct")
    private List<OrderProductOption> orderProductOptions = new ArrayList<>();

    @OneToMany(mappedBy = "orderProduct")
    private List<OrderProductOptionChoice> orderProductOptionChoice = new ArrayList<>();

    /**
     * 비즈니스 로직
     */

    public static OrderProduct createOrderProductOption(List<OrderProductOption> orderProductOptions) {
        OrderProduct orderProduct = new OrderProduct();
        for (OrderProductOption opo : orderProductOptions) {
            orderProduct.addOrderProductOption(opo);
        }
        return orderProduct;
    }

    private void addOrderProductOption(OrderProductOption orderProductOption) {
        orderProductOptions.add(orderProductOption);
        orderProductOption.setOrderProduct(this);
    }
}
