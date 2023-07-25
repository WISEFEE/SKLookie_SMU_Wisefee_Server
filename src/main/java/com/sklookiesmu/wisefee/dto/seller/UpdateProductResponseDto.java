package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 수정 응답 DTO")
public class UpdateProductResponseDto {

    @ApiModelProperty(value = "상품 ID", required = true)
    private Long productId;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품 가격")
    private int productPrice;

    @ApiModelProperty(value = "상품 설명")
    private String productInfo;
}
