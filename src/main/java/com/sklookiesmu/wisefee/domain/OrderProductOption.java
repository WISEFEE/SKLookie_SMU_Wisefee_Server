package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_PRODUCT_OPTION")
public class OrderProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT_OPTION_ID")
    private Long orderProductOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_OPTION_ID")
    private ProductOption productOption;

    @OneToMany(mappedBy = "orderProductOption")
    private List<OrderProductOptionChoice> orderProductOptChoice = new ArrayList<>();

    public static OrderProductOption createOrderProductOptionChoice(List<OrderProductOptionChoice> productOptChoice) {
        OrderProductOption orderProductOption = new OrderProductOption();
        for (OrderProductOptionChoice poc : productOptChoice) {
            orderProductOption.addOrderProductOptionChoice(poc);
        }
        return orderProductOption;
    }

    private void addOrderProductOptionChoice(OrderProductOptionChoice orderProductOptionChoice) {
        orderProductOptChoice.add(orderProductOptionChoice);
        orderProductOptionChoice.setOrderProductOption(this);
    }
}
