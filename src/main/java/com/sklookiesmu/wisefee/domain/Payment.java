package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "PAYMENT_PRICE", nullable = false)
    private Integer paymentPrice;

    @Column(name = "PAYMENT_INFO")
    private String paymentInfo;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

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
    // 결제내역에서 구독이나 주문정보를 가져올 일은 없을 것 같아 단방향으로 설정.
//    @OneToOne(mappedBy = "payment", orphanRemoval = true)
//    private Subscribe subscribe;
//    @OneToOne(mappedBy = "payment", orphanRemoval = true)
//    private Order order;
}
