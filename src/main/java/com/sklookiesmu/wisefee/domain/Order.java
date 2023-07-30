package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "TUMBLR_STATUS")
    private String tumblrStatus;

    @Column(name = "PRODUCT_RECEIVE_TIME")
    private LocalDateTime productReceiveTime;

    @Column(name = "PRODUCT_STATUS")
    private String productStatus;

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

    /**
     * 연관관계 매핑
     */
    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderOption> orderOptions = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_ID", nullable = false)
    private Payment payment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_ID")
    private Subscribe subscribe;
    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();
//    @OneToMany(mappedBy = "order_payment")
//    private List<OrderProduct> orderProduct_payments = new ArrayList<>();
}
