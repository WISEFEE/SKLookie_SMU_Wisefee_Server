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
@Table(name = "FILE")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;

    @Column(name = "FILE_TYPE", nullable = false)
    private String fileType;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "FILE_CAPACITY", nullable = false)
    private String fileCapacity;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean isDeleted;

    @Column(name = "FILE_INFO")
    private String fileInfo;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "file")
    private List<Cafe> cafes = new ArrayList<>();
    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    private ProductImage productImage;
}
