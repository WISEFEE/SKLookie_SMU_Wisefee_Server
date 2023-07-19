package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "PAYMENT_PRICE", nullable = false)
    private Integer paymentPrice;

    @Column(name = "PAYMENT_INFO", nullable = false, length = 1000)
    private String paymentInfo;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * 연관관계 매핑
     */
    // 결제내역에서 구독이나 주문정보를 가져올 일은 없을 것 같아 단방향으로 설정.
//    @OneToOne(mappedBy = "payment", orphanRemoval = true)
//    private Subscribe subscribe;
//    @OneToOne(mappedBy = "payment", orphanRemoval = true)
//    private Order order;
}
