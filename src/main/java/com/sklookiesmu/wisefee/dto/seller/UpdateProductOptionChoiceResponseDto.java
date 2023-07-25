package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 선택지 수정 응답 DTO")
public class UpdateProductOptionChoiceResponseDto {

    @ApiModelProperty(value = "상품 옵션 선택지 ID", required = true)
    private Long productOptionChoiceId;

    @ApiModelProperty(value = "상품 옵션 선택지명")
    private String productOptionChoiceName;

    @ApiModelProperty(value = "상품 옵션 선택지 추가 가격")
    private int productOptionChoicePrice;
}
