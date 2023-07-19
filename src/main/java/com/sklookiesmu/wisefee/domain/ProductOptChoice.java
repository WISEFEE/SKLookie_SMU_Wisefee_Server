package com.sklookiesmu.wisefee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "PRD_OPTCHOICE_PRICE", nullable = false)
    private int productOptionChoicePrice;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    /**
     * 연관관계 매핑
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRD_OPTION_ID", nullable = false)
    private ProductOption productOption;
}
