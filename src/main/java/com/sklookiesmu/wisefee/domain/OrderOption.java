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
@Table(name = "ORDER_OPTION")
public class OrderOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_OPTION_ID")
    private Long orderOptionId;

    @Column(name = "ORDER_OPTION_NAME", nullable = false)
    private String orderOptionName;

    @Column(name = "ORDER_OPTION_PRICE")
    private int orderOptionPrice;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAFE_ID", nullable = false)
    private Cafe cafe;


    @ManyToMany
    @JoinTable(name = "ORDER_ORDEROPTION",
            joinColumns = @JoinColumn(name = "ORDER_OPTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ORDER_ID")
    )
    private List<Order> orders = new ArrayList<>();
}
