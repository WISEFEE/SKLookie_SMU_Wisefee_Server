package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_PRODUCT_OPTION_CHOICE")
public class OrderProductOptionChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT_OPTION_CHOICE_ID")
    private Long orderProductOptionChoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_OPTION_ID")
    private OrderProductOption orderProductOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_OPT_CHOICE")
    private ProductOptChoice productOptChoice;

    public static OrderProductOptionChoice createOrderProdOptChoice(OrderProduct orderProduct, OrderProductOption orderProductOption,
                                                                    ProductOptChoice productOptChoice){
        OrderProductOptionChoice orderProductOptionChoice = new OrderProductOptionChoice();
        orderProductOptionChoice.setOrderProduct(orderProduct);
        orderProductOptionChoice.setOrderProductOption(orderProductOption);
        orderProductOptionChoice.setProductOptChoice(productOptChoice);

        return orderProductOptionChoice;
    }
}
