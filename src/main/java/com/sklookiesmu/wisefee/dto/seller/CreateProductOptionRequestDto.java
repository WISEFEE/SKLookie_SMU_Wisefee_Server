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
@ApiModel(description = "상품 옵션 추가 요청 DTO")
public class CreateProductOptionRequestDto {

    @ApiModelProperty(value = "상품 옵션명", required = true)
    @NotNull
    private String productOptionName;
}
