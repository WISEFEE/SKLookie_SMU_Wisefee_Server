package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "주문 옵션 추가 요청 DTO")
public class CreateOrderOptionRequestDto {

    @ApiModelProperty(value = "주문 옵션명", required = true)
    private String orderOptionName;

    @ApiModelProperty(value = "주문 옵션 추가 금액", required = true)
    private int orderOptionPrice;
}
