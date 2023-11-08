package com.sklookiesmu.wisefee.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORD_ORDER_OPTION")
public class OrdOrderOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ORDER_OPTION_ID")
    private Long ordOrderOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_OPTION_ID")
    private OrderOption orderOption;

    /**
     * 비즈니스 로직
     */
    public static OrdOrderOption createOrdOrderOption(OrderOption orderOption){
        OrdOrderOption ordOrderOption = new OrdOrderOption();
        ordOrderOption.setOrderOption(orderOption);

        return ordOrderOption;
    }
}
