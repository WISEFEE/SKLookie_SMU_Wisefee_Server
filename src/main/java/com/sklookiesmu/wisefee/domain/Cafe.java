package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "CAFE")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAFE_ID")
    private Long cafeId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CAFE_PHONE", nullable = false)
    private String cafePhone;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

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
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    private File file;
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDR_ID", nullable = false)
    private Address address;
    @OneToMany(mappedBy = "cafe")
    private List<Cart> carts = new ArrayList<>();
    @OneToMany(mappedBy = "cafe")
    private List<Subscribe> subscribes = new ArrayList<>();
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<OrderOption> orderOptions = new ArrayList<>();
}
