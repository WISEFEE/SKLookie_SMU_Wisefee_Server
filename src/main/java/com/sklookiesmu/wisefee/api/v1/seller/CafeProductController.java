package com.sklookiesmu.wisefee.api.v1.seller;


import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.service.seller.CafeProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "SELL-C :: 매장 상품 편집 API")
@RestController
@RequiredArgsConstructor
public class CafeProductController {

    private final CafeProductService cafeProductService;

    @ApiOperation(
            value = "SELL-C-01 :: 상품 추가",
            notes = "상품 추가 API입니다. <br>" +
                    "상품 정보, 상품명, 상품 가격을 입력합니다."
    )
    @PostMapping("/api/v1/seller/{cafeId}/product")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponseDto createProduct(@PathVariable("cafeId") Long cafeId,
                                                  @RequestBody @Valid CreateProductRequestDto requestDto) {
        Long productId = cafeProductService.addProduct(cafeId, requestDto);

        return new CreateProductResponseDto(productId);
    }


//    @ApiOperation(
//            value = "상품 수정",
//            notes = "상품 수정 API입니다. <br>" +
//                    "수정할 상품 정보, 상품명, 상품 가격을 입력합니다."
//    )
//    @PutMapping("/api/v1/seller/{cafeId}/product/{productId}")
//    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
//    public UpdateProductResponseDto updateProduct(@PathVariable("cafeId") Long cafeId,
//                                                  @PathVariable("productId") Long productId,
//                                                  @RequestBody @Valid UpdateProductRequestDto requestDto) {
//        cafeProductService.updateProduct(cafeId, productId, requestDto);
//        Product findProduct = cafeProductService.findProduct(productId);
//
//        return new UpdateProductResponseDto(findProduct.getProductId(), findProduct.getProductName(), findProduct.getProductPrice(), findProduct.getProductInfo());
//    }


    @ApiOperation(
            value = "SELL-C-02 :: 상품 삭제",
            notes = "상품 삭제 API입니다. <br>" +
                    "소프트 삭제를 하여 상품의 deleted_at 속성을 삭제 시점으로 설정합니다."
    )
    @DeleteMapping("/api/v1/seller/{cafeId}/product/{productId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteProduct(@PathVariable("cafeId") Long cafeId,
                              @PathVariable("productId") Long productId) {
        cafeProductService.deleteProduct(cafeId, productId);
    }


    @ApiOperation(
            value = "SELL-C-03 :: 상품 목록 조회",
            notes = "소프트 삭제되지 않은 상품 목록을 조회하는 API입니다."
    )
    @GetMapping("/api/v1/seller/{cafeId}/products")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public List<ProductsDto> getProducts(@PathVariable("cafeId") Long cafeId) {
        List<Product> Products = cafeProductService.getProductsByCafeId(cafeId);

        return Products.stream()
                .map(ProductsDto::fromProduct)
                .collect(Collectors.toList());
    }


    @ApiOperation(
            value = "SELL-C-04 :: 상품 옵션 추가",
            notes = "상품 옵션 추가 API입니다. <br>" +
                    "상품 옵션명을 입력합니다."
    )
    @PostMapping("/api/v1/seller/{productId}/productOption")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductOptionResponseDto createProductOption(@PathVariable("productId") Long productId,
                                                              @RequestBody @Valid CreateProductOptionRequestDto requestDto) {
        Long productOptionId = cafeProductService.addProductOption(productId, requestDto);

        return new CreateProductOptionResponseDto(productOptionId);
    }


//    @ApiOperation(
//            value = "상품 옵션 수정",
//            notes = "상품 옵션 수정 API입니다. <br>" +
//                    "수정할 상품 옵션명을 입력합니다."
//    )
//    @PutMapping("/api/v1/seller/{productId}/productOption/{productOptionId}")
//    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
//    public UpdateProductOptionResponseDto updateProductOption(@PathVariable("productId") Long productId,
//                                                              @PathVariable("productOptionId") Long productOptionId,
//                                                              @RequestBody @Valid UpdateProductOptionRequestDto requestDto
//    ) {
//        cafeProductService.updateProductOption(productId, productOptionId, requestDto);
//        ProductOption findProductOption = cafeProductService.findProductOption(productOptionId);
//
//        return new UpdateProductOptionResponseDto(findProductOption.getProductOptionId(), findProductOption.getProductOptionName());
//    }


    @ApiOperation(
            value = "SELL-C-05 :: 상품 옵션 삭제",
            notes = "상품 옵션 삭제 API입니다. <br>" +
                    "소프트 삭제를 하여 상품 옵션의 deleted_at 속성을 삭제 시점으로 설정합니다."
    )
    @DeleteMapping("/api/v1/seller/{productId}/productOption/{productOptionId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteProductOption(@PathVariable("productId") Long productId,
                                    @PathVariable("productOptionId") Long productOptionId)
    {
        cafeProductService.deleteProductOption(productId, productOptionId);
    }


    @ApiOperation(
            value = "SELL-C-06 :: 상품 옵션 선택지 추가",
            notes = "상품 옵션 선택지 추가 API입니다. <br>" +
                    "상품 옵션 선택지명과 추가 가격을 입력합니다."
    )
    @PostMapping("/api/v1/seller/{productOptionId}/productOptionChoice")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductOptionChoiceResponseDto addProductOptionChoice(@PathVariable("productOptionId") Long productOptionId,
                                                                       @RequestBody @Valid CreateProductOptionChoiceRequestDto requestDto) {
        Long productOptionChoiceId = cafeProductService.addProductOptionChoice(productOptionId, requestDto);

        return new CreateProductOptionChoiceResponseDto(productOptionChoiceId);
    }


//    @ApiOperation(
//            value = "상품 옵션 선택지 수정",
//            notes = "상품 옵션 선택지 수정 API입니다. <br>" +
//                    "수정할 상품 옵션 선택지명과 추가 가격을 입력합니다."
//    )
//    @PutMapping("/api/v1/seller/{productId}/productOption/{productOptionId}/productOptionChoice/{productOptionChoiceId}")
//    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
//    public UpdateProductOptionChoiceResponseDto updateProductOptionChoice(@PathVariable Long productId,
//                                                                          @PathVariable("productOptionId") Long productOptionId,
//                                                                          @PathVariable("productOptionChoiceId") Long productOptionChoiceId,
//                                                                          @RequestBody @Valid UpdateProductOptionChoiceRequestDto requestDto) {
//        cafeProductService.updateProductOptionChoice(productId, productOptionId, productOptionChoiceId, requestDto);
//        ProductOptChoice findProductOptionChoice = cafeProductService.findProductOptionChoice(productOptionChoiceId);
//
//        return new UpdateProductOptionChoiceResponseDto(findProductOptionChoice.getProductOptionChoiceId(), findProductOptionChoice.getProductOptionChoiceName(), findProductOptionChoice.getProductOptionChoicePrice());
//    }


    @ApiOperation(
            value = "SELL-C-07 :: 상품 옵션 선택지 삭제",
            notes = "상품 옵션 선택지 삭제 API입니다. <br>" +
                    "소프트 삭제를 하여 상품 옵션 선택지의 deleted_at 속성을 삭제 시점으로 설정합니다."
    )
    @DeleteMapping("/api/v1/seller/{productId}/productOption/{productOptionId}/productOptionChoice/{productOptionChoiceId}")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void deleteProductOptionChoice(@PathVariable("productId") Long productId,
                                          @PathVariable("productOptionId") Long productOptionId,
                                          @PathVariable("productOptionChoiceId") Long productOptionChoiceId) {
        cafeProductService.deleteProductOptionChoice(productId, productOptionId, productOptionChoiceId);
    }

}
