package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.OrderOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "주문 옵션 리스트 DTO")
public class OrderOptionsDto {

    @ApiModelProperty(value = "주문 옵션 ID")
    private Long orderOptionId;

    @ApiModelProperty(value = "주문 옵션명")
    private String orderOptionName;

    @ApiModelProperty(value = "주문 옵션 가격")
    private int orderOptionPrice;

    public static OrderOptionsDto fromOrderOption(OrderOption orderOption) {
        return new OrderOptionsDto(
                orderOption.getOrderOptionId(),
                orderOption.getOrderOptionName(),
                orderOption.getOrderOptionPrice()
        );
    }
}
