package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 선택지 수정 요청 DTO")
public class UpdateProductOptionChoiceRequestDto {

    @ApiModelProperty(value = "상품 옵션 선택지명", example = "그란데")
    private String productOptionChoiceName;

    @ApiModelProperty(value = "상품 옵션 선택지 추가 금액", example = "1500")
    private int productOptionChoicePrice;
}
