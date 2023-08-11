package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 선택지 리스트 DTO")
public class ProductOptionChoicesDto {

    @ApiModelProperty(value = "상품 옵션 선택지 ID")
    private Long productOptionChoiceId;

    @ApiModelProperty(value = "상품 옵션 선택지명")
    private String productOptionChoiceName;

    @ApiModelProperty(value = "상품 옵션 선택지 가격")
    private int productOptionChoicePrice;

    public static ProductOptionChoicesDto fromProductOptionChoices(ProductOptChoice productOptChoice) {
        return new ProductOptionChoicesDto(
                productOptChoice.getProductOptionChoiceId(),
                productOptChoice.getProductOptionChoiceName(),
                productOptChoice.getProductOptionChoicePrice()
        );
    }
}
