package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDR_ID")
    private Long addrId;

    @Column(name = "ADDRESS_NAME", nullable = false)
    private String addressName;

    @Column(name = "REGION_1_DEPTH_NAME", nullable = false)
    private String region1DepthName;

    @Column(name = "REGION_2_DEPTH_NAME", nullable = false)
    private String region2DepthName;

    @Column(name = "REGION_3_DEPTH_NAME", nullable = false)
    private String region3DepthName;

    @Column(name = "ADDRESS_DETAIL", nullable = false)
    private String addressDetail;

    @Column(name = "X")
    private Double x;

    @Column(name = "Y")
    private Double y;

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
    // 주소에서 회원정보를 접근할 일은 없어서 단방향으로 설정
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Member member;
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Cafe cafe;
}
