package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 추가 응답 DTO")
public class CreateProductResponseDto {

    @ApiModelProperty(value = "상품 ID", required = true)
    private Long productId;
}
