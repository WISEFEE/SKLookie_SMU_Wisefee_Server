package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 선택지 추가 요청 DTO")
public class CreateProductOptionChoiceRequestDto {

    @ApiModelProperty(value = "상품 옵션 선택지명", required = true)
    @NotNull
    private String productOptionChoiceName;

    @ApiModelProperty(value = "상품 옵션 선택지 추가 금액", required = true)
    @NotNull
    private int productOptionChoicePrice;
}
