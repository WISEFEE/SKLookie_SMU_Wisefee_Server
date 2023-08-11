package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_OPT_CHOICE")
public class ProductOptChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_OPTCHOICE_ID")
    private Long productOptionChoiceId;

    @Column(name = "PRD_OPTCHOICE_NAME", nullable = false)
    private String productOptionChoiceName;

    @Column(name = "PRD_OPTCHOICE_PRICE")
    private int productOptionChoicePrice;

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
    @JoinColumn(name = "PRD_OPTION_ID", nullable = false)
    private ProductOption productOption;

    @OneToMany(mappedBy = "productOptChoice")
    private List<CartProductChoiceOption> cartProductChoiceOptions;

    @Override
    public String toString() {
        return "ProductOptChoice{" +
                "productOptionChoiceId=" + productOptionChoiceId +
                ", productOptionChoiceName='" + productOptionChoiceName + '\'' +
                ", productOptionChoicePrice=" + productOptionChoicePrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", productOption=" + productOption +
                ", cartProductChoiceOptions=" + cartProductChoiceOptions +
                '}';
    }
}
