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

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column(name = "ADDRESS_NAME", nullable = false)
    private String addressName;

    @Column(name = "ADDRESS_DETAIL", nullable = false)
    private String addressDetail;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "LNG")
    private Integer lng;

    @Column(name = "LAT")
    private Integer lat;

    @Column(name = "PREFER_LOCATION")
    private String preferLocation;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    /**
     * 연관관계 매핑
     */
    // 주소에서 회원정보를 접근할 일은 없어서 단방향으로 설정
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Member member;
//    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private Cafe cafe;
}
