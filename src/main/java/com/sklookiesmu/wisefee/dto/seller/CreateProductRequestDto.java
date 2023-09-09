package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 추가 요청 DTO")
public class CreateProductRequestDto {

    @ApiModelProperty(value = "상품명", required = true, example = "아메리카노")
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String productName;

    @ApiModelProperty(value = "상품 가격", required = true, example = "2900")
    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private int productPrice;

    @ApiModelProperty(value = "상품 설명", example = "페루의 원두로 내린 아메리카노 입니다.")
    private String productInfo;
}
