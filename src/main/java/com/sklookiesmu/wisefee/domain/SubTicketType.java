package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "SUBTICKET_TYPE")
public class SubTicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBTCT_ID")
    private Long subTicketId;

    @Column(name = "SUBTCT_NAME", nullable = false)
    private String subTicketName;

    @Column(name = "SUBTCT_PRICE", nullable = false)
    private int subTicketPrice;

    @Column(name = "SUBTCT_MIN_USER_CNT", nullable = false)
    private int subTicketMinUserCount;

    @Column(name = "SUBTCT_MAX_USER_CNT", nullable = false)
    private int subTicketMaxUserCount;

    @Column(name = "SUBTCT_DEPOSIT", nullable = false)
    private int subTicketDeposit;

    @Column(name = "SUBTCT_BASE_DISCNT_RATE", nullable = false)
    private int subTicketBaseDiscountRate;

    @Column(name = "SUBTCT_ADT_DISCNT_RATE", nullable = false)
    private int subTicketAdditionalDiscountRate;

    @Column(name = "SUBTCT_MAX_DISCNT_RATE", nullable = false)
    private int subTicketMaxDiscountRate;

    @Column(name = "SUBTCT_DESC", columnDefinition = "TEXT")
    private String subTicketDescription;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;


    /**
     * 연관관계 매핑
     */
    // 구독권에서 구독 정보를 가져올 일은 없을 것 같아 단방향 설정.
//    @OneToOne(mappedBy = "subTicketType")
//    private Subscribe subscribe;
}
