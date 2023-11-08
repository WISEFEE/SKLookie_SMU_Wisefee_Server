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
@ApiModel(description = "상품 옵션 수정 응답 DTO")
public class UpdateProductOptionResponseDto {

    @ApiModelProperty(value = "상품 옵션 ID", required = true)
    private Long productOptionId;

    @ApiModelProperty(value = "상품 옵션명")
    private String productOptionName;
}
