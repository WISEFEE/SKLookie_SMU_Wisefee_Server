package com.sklookiesmu.wisefee.domain;

import com.sklookiesmu.wisefee.common.constant.ProductStatus;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TUMBLR_STATUS")
    private TumblrStatus tumblrStatus;

    @Column(name = "PRODUCT_RECEIVE_TIME")
    private LocalDateTime productReceiveTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_STATUS")
    private ProductStatus productStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    /**
     * 생성일 값 세팅
     */
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }


    /**
     * 연관관계 매핑
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_ID")
    private Subscribe subscribe;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();
//    @OneToMany(mappedBy = "order_payment")
//    private List<OrderProduct> orderProduct_payments = new ArrayList<>();

    @ManyToMany(mappedBy = "orders")
    private List<OrderOption> orderOptions = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrdOrderOption> ordOrderOptions = new ArrayList<>();

    /**
     * 비즈니스 로직
     */
    public static Order createOrder(Order order, List<OrderProduct> orderProducts, List<OrdOrderOption> ordOrderOptions){
        for (OrderProduct op : orderProducts) {
            order.addOrderProduct(op);
        }
        for (OrdOrderOption oop : ordOrderOptions) {
            order.addOrdOrderOptions(oop);
        }
        return order;
    }

    private void addOrdOrderOptions(OrdOrderOption ordOrderOption) {
        ordOrderOptions.add(ordOrderOption);
        ordOrderOption.setOrder(this);
    }

    private void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

}
