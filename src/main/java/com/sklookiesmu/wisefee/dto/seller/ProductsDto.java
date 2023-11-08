package com.sklookiesmu.wisefee.dto.seller;

import com.sklookiesmu.wisefee.domain.File;
import com.sklookiesmu.wisefee.domain.Product;
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
@ApiModel(description = "상품 리스트 DTO")
public class ProductsDto {

    @ApiModelProperty(value = "상품 ID")
    private Long productId;

    @ApiModelProperty(value = "상품명")
    private String productName;

    @ApiModelProperty(value = "상품 파일 ID 리스트")
    private List<Long> fileIds;

    @ApiModelProperty(value = "상품 가격")
    private int productPrice;

    @ApiModelProperty(value = "상품 정보")
    private String productInfo;

    @ApiModelProperty(value = "매장 상품 옵션 리스트")
    private List<ProductOptionsDto> productOptions;

    public static ProductsDto fromProduct(Product product) {
        List<ProductOptionsDto> productOptionsDtoList = product.getProductOptions().stream()
                .filter(productOption -> productOption.getDeletedAt() == null)
                .map(productOption -> ProductOptionsDto.fromProductOption(productOption))
                .collect(Collectors.toList());

        List<Long> fileIds = product.getFiles().stream()
                .map(File::getFileId)
                .collect(Collectors.toList());

        return new ProductsDto(
                product.getProductId(),
                product.getProductName(),
                fileIds,
                product.getProductPrice(),
                product.getProductInfo(),
                productOptionsDtoList
        );
    }
}
