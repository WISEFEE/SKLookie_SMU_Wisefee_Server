package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 수정 요청 DTO")
public class UpdateProductOptionRequestDto {

    @ApiModelProperty(value = "상품 옵션명", example = "사이즈 업")
    private String productOptionName;
}
