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
@ApiModel(description = "상품 옵션 선택지 추가 요청 DTO")
public class CreateProductOptionChoiceRequestDto {

    @ApiModelProperty(value = "상품 옵션 선택지명", required = true)
    @NotBlank(message = "상품 옵션 선택지명은 필수 입력 값입니다.")
    private String productOptionChoiceName;

    @ApiModelProperty(value = "상품 옵션 선택지 추가 금액", required = true)
    @NotNull(message = "상품 옵션 선택지 추가 금액은 필수 입력 값입니다.")
    private int productOptionChoicePrice;
}
