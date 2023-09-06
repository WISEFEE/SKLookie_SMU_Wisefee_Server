package com.sklookiesmu.wisefee.domain;

import lombok.*;
import org.springframework.lang.Nullable;


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
@Table(name = "SUBSCRIBE")
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUB_ID")
    private Long subId;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @Column(name = "SUB_COMMENT")
    private String subComment;

    @Column(name = "SUB_PEOPLE", nullable = false)
    private Integer subPeople;

    @Column(name="SUB_CNT")
    private int subCnt; // 사용 누적 횟수

    @Column(name="SUBCNT_DAY")
    private int subCntDay; // 1일 1회 제한

    @Column(name="SUB_STATUS")
    private String subStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "EXPIRED_AT")
    private LocalDateTime expiredAt;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAFE_ID")
    private Cafe cafe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_ID", nullable = false)
    private Payment payment;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBTCT_ID", nullable = false)
    private SubTicketType subTicketType;
}
