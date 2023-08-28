package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import java.util.List;
import java.util.stream.Collectors;

public class ProductDto {

    // 카페 음료 리스트 -> 음료 옵션 -> 음료 옵션 선택
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductResponseDto{

        @ApiModelProperty(value = "제품 PK", required = true)
        private Long productId;

        @ApiModelProperty(value = "제품명", required = true)
        private String productName;

        @ApiModelProperty(value = "제품가격", required = true)
        private int productPrice;

        @ApiModelProperty(value = "제품정보", required = true)
        private String productInfo;

        @ApiModelProperty(value = "제품옵션", required = true)
        private List<ProductOptionResponseDto> productOptions;

        public static ProductResponseDto from(Product product){
            return new ProductResponseDto(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getProductInfo(),
                    product.getProductOptions().stream()
                            .map(ProductOptionResponseDto::from)
                            .collect(Collectors.toList()));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ProductListResponseDto{
        private List<ProductResponseDto> products;
        public static ProductListResponseDto of(List<Product> productList){
            List<ProductResponseDto> productResponses = productList
                    .stream()
                    .map(ProductResponseDto::from)
                    .collect(Collectors.toList());

            return new ProductListResponseDto(productResponses);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOptionResponseDto{

        @ApiModelProperty(value = "제품옵션 PK", required = true)
        private Long productOptionId;

        @ApiModelProperty(value = "제품옵션 명")
        private String productOptionName;

        @ApiModelProperty(value = "제품옵션 선택", required = true)
        private List<ProductOptChoiceResponseDto> productOptChoice;

        public static ProductOptionResponseDto from (ProductOption productOption){
            return new ProductOptionResponseDto(
                    productOption.getProductOptionId(),
                    productOption.getProductOptionName(),
                    productOption.getProductOptChoices().stream()
                            .map(ProductOptChoiceResponseDto::from)
                            .collect(Collectors.toList()));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ProductOptionListResponseDto{
        private List<ProductOptionResponseDto> productOptionList;

        public static ProductOptionListResponseDto of(List<ProductOption> productOptions){
            List<ProductOptionResponseDto> productOptionResponse = productOptions
                    .stream()
                    .map(ProductOptionResponseDto::from)
                    .collect(Collectors.toList());

            return new ProductOptionListResponseDto(productOptionResponse);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOptChoiceResponseDto{

        @ApiModelProperty(value = "제품옵션 PK", required = true)
        private Long productOptionChoiceId;

        @ApiModelProperty(value = "제품옵션 선택 명", required = true)
        private String productOptionChoiceName;

        @ApiModelProperty(value = "제품옵션 선택 가격", required = true)
        private int productOptionChoicePrice;

        public static ProductOptChoiceResponseDto from (ProductOptChoice productOptChoice){
            return new ProductOptChoiceResponseDto(
                    productOptChoice.getProductOptionChoiceId(),
                    productOptChoice.getProductOptionChoiceName(),
                    productOptChoice.getProductOptionChoicePrice());
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductOptionRequestDto{

        @ApiModelProperty(value = "제품 옵션 PK", required = true)
        private Long productOptionId;

        @ApiModelProperty(value = "제품 옵션 선택 PK", required = true)
        private List<ProductOptionChoiceRequestDto> productOptionChoice;

        public static ProductOptionRequestDto prodOptToDto(ProductOption productOption) {
            return ProductOptionRequestDto.builder()
                    .productOptionId(productOption.getProductOptionId())
                    .productOptionChoice(productOption.getProductOptChoices().stream()
                            .map((productOptChoice -> ProductOptionChoiceRequestDto.builder()
                                    .optionChoiceId(productOptChoice.getProductOptionChoiceId())
                                    .build())).collect(Collectors.toList())).build();
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductOptionChoiceRequestDto{

        @ApiModelProperty(value = "선택 상품옵션 아이디", example = "1")
        private Long optionChoiceId;
    }

    @Getter
    @AllArgsConstructor
    public static class ProductOptChoiceListResponseDto{
        private List<ProductOptChoiceResponseDto> productOptChoiceList;

        public static ProductOptChoiceListResponseDto of(List<ProductOptChoice> productOptChoices){
            List<ProductOptChoiceResponseDto> productOptChoiceResponse = productOptChoices
                    .stream()
                    .map(ProductOptChoiceResponseDto::from)
                    .collect(Collectors.toList());

            return new ProductOptChoiceListResponseDto(productOptChoiceResponse);
        }
    }
}
