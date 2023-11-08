package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "주문 옵션 수정 요청 DTO")
public class UpdateOrderOptionRequestDto {

    @ApiModelProperty(value = "주문 옵션명", example = "빨대 빼주세요")
    private String orderOptionName;

    @ApiModelProperty(value = "주문 옵션 추가 금액", example = "0")
    private int orderOptionPrice;
}
