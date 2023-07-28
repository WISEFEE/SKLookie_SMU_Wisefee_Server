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
@ApiModel(description = "상품 추가 요청 DTO")
public class CreateProductRequestDto {

    @ApiModelProperty(value = "상품명", required = true)
    @NotNull
    private String productName;

    @ApiModelProperty(value = "상품 가격", required = true)
    @NotNull
    private int productPrice;

    @ApiModelProperty(value = "상품 설명")
    private String productInfo;
}
