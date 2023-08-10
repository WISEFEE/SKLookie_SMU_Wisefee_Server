package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 리스트 DTO")
public class ProductsDto {

    @ApiModelProperty(value = "상품 ID")
    private Long productId;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품 가격")
    private int productPrice;

    @ApiModelProperty(value = "상품 정보")
    private String productInfo;
}
