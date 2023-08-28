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
    @JoinColumn(name = "ORDER_PRODUCT")
    private OrderProduct orderProduct;

    @OneToMany(mappedBy = "orderProductOption")
    private List<ProductOptChoice> productOptChoice;

    public static OrderProductOption createOrderProductOptionChoice(List<ProductOptChoice> productOptChoice) {
        OrderProductOption orderProductOption = new OrderProductOption();
        for (ProductOptChoice poc : productOptChoice) {
            orderProductOption.addOrderProductOptionChoice(poc);
        }
        return orderProductOption;
    }

    private void addOrderProductOptionChoice(ProductOptChoice orderProductOptionChoice) {
        orderProductOptionChoice.setOrderProductOptionId(this);
    }
}
