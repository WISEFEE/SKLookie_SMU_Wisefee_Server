package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.ProductOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "상품 옵션 리스트 DTO")
public class ProductOptionsDto {

    @ApiModelProperty(value = "상품 옵션 ID")
    private Long productOptionId;

    @ApiModelProperty(value = "상품 옵션명")
    private String productOptionName;

    @ApiModelProperty(value = "상품 옵션 선택지 리스트")
    private List<ProductOptionChoicesDto> productOptionChoices;

    public static ProductOptionsDto fromProductOption(ProductOption productOption) {
        List<ProductOptionChoicesDto> productOptionChoicesDtoList = productOption.getProductOptChoices().stream()
                .map(productOptionChoice -> ProductOptionChoicesDto.fromProductOptionChoices(productOptionChoice))
                .collect(Collectors.toList());

        return new ProductOptionsDto(
                productOption.getProductOptionId(),
                productOption.getProductOptionName(),
                productOptionChoicesDtoList
        );
    }
}
