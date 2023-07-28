package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;
import com.sklookiesmu.wisefee.service.seller.CafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "매장 API")
@RestController
@RequiredArgsConstructor
public class CafeApiController {

    private final CafeService cafeService;

    @ApiOperation(value = "매장 등록")
    @PostMapping("/api/v1/cafe")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCafeResponseDto createCafe(@RequestBody @Valid CreateCafeRequestDto requestDto) {
        Cafe cafe = new Cafe();
        cafe.setTitle(requestDto.getTitle());
        cafe.setTitle(requestDto.getContent());
        cafe.setTitle(requestDto.getCafePhone());

        Long id = cafeService.create(cafe);
        return new CreateCafeResponseDto(id);
    }


    @ApiOperation(value = "매장 정보 수정")
    @PutMapping("/api/v1/cafe/{cafeId}")
    public UpdateCafeResponseDto updateCafe(@PathVariable("cafeId") Long cafeId,
                                            @RequestBody @Valid UpdateCafeRequestDto requestDto) {
        cafeService.update(cafeId, requestDto);
        Cafe findCafe = cafeService.findCafe(cafeId);

        return new UpdateCafeResponseDto(findCafe.getCafeId(), findCafe.getTitle(), findCafe.getContent(), findCafe.getCafePhone());
    }


    @ApiOperation(value = "매장 삭제")
    @DeleteMapping("/api/v1/cafe/{cafeId}")
    public void deleteCafe(@PathVariable("cafeId") Long cafeId) {
        cafeService.delete(cafeId);
    }

    @ApiOperation(value = "주문 옵션 추가")
    @PostMapping("/api/v1/{cafeId}/orderOption")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderOptionResponseDto createOrderOption(@PathVariable("cafeId") Long cafeId,
                                                          @RequestBody @Valid CreateOrderOptionRequestDto requestDto) {
        Long orderOptionId = cafeService.addOrderOption(cafeId, requestDto);

        return new CreateOrderOptionResponseDto(orderOptionId);
    }


    @ApiOperation(value = "주문 옵션 수정")
    @PutMapping("/api/v1/{cafeId}/orderOption/{orderOptionId}")
    public UpdateOrderOptionResponseDto updateOrderOption(@PathVariable("cafeId") Long cafeId,
                                                          @PathVariable("orderOptionId") Long orderOptionId,
                                                          @RequestBody @Valid UpdateOrderOptionRequestDto requestDto) {
        cafeService.updateOrderOption(cafeId, orderOptionId, requestDto);
        OrderOption findOrderOption = cafeService.findOrderOption(orderOptionId);

        return new UpdateOrderOptionResponseDto(findOrderOption.getOrderOptionId(), findOrderOption.getOrderOptionName(), findOrderOption.getOrderOptionPrice());
    }


    @ApiOperation(value = "주문 옵션 삭제")
    @DeleteMapping("/api/v1/{cafeId}/orderOption/{orderOptionId}")
    public void deleteOrderOption(@PathVariable("cafeId") Long cafeId,
                                  @PathVariable("orderOptionId") Long orderOptionId) {
        cafeService.deleteOrderOption(cafeId, orderOptionId);
    }


    @ApiOperation(value = "상품 추가")
    @PostMapping("/api/v1/{cafeId}/product")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponseDto createProduct(@PathVariable("cafeId") Long cafeId,
                                                  @RequestBody @Valid CreateProductRequestDto requestDto) {
        Long productId = cafeService.addProduct(cafeId, requestDto);

        return new CreateProductResponseDto(productId);
    }


    @ApiOperation(value = "상품 수정")
    @PutMapping("/api/v1/{cafeId}/product/{productId}")
    public UpdateProductResponseDto updateProduct(@PathVariable("cafeId") Long cafeId,
                                                  @PathVariable("productId") Long productId,
                                                  @RequestBody @Valid UpdateProductRequestDto requestDto) {
        cafeService.updateProduct(cafeId, productId, requestDto);
        Product findProduct = cafeService.findProduct(productId);

        return new UpdateProductResponseDto(findProduct.getProductId(), findProduct.getProductName(), findProduct.getProductPrice(), findProduct.getProductInfo());
    }


    @ApiOperation(value = "상품 삭제")
    @DeleteMapping("/api/v1/{cafeId}/product/{productId}")
    public void deleteProduct(@PathVariable("cafeId") Long cafeId,
                              @PathVariable("productId") Long productId) {
        cafeService.deleteProduct(cafeId, productId);
    }


    @ApiOperation(value = "상품 옵션 추가")
    @PostMapping("/api/v1/{productId}/productOption")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductOptionResponseDto createProductOption(@PathVariable("productId") Long productId,
                                                              @RequestBody @Valid CreateProductOptionRequestDto requestDto) {
        Long productOptionId = cafeService.addProductOption(productId, requestDto);

        return new CreateProductOptionResponseDto(productOptionId);
    }


    @ApiOperation(value = "상품 옵션 수정")
    @PutMapping("/api/v1/{productId}/productOption/{productOptionId}")
    public UpdateProductOptionResponseDto updateProductOption(@PathVariable("productId") Long productId,
                                                              @PathVariable("productOptionId") Long productOptionId,
                                                              @RequestBody @Valid UpdateProductOptionRequestDto requestDto
    ) {
        cafeService.updateProductOption(productId, productOptionId, requestDto);
        ProductOption findProductOption = cafeService.findProductOption(productOptionId);

        return new UpdateProductOptionResponseDto(findProductOption.getProductOptionId(), findProductOption.getProductOptionName());
    }


    @ApiOperation(value = "상품 옵션 삭제")
    @DeleteMapping("/api/v1/{productId}/productOption/{productOptionId}")
    public void deleteProductOption(@PathVariable("productId") Long productId,
                                    @PathVariable("productOptionId") Long productOptionId)
    {
        cafeService.deleteProductOption(productId, productOptionId);
    }


    @ApiOperation(value = "상품 옵션 선택지 추가")
    @PostMapping("/api/v1/{productOptionId}/productOptionChoice")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductOptionChoiceResponseDto addProductOptionChoice(@PathVariable("productOptionId") Long productOptionId,
                                                                       @RequestBody @Valid CreateProductOptionChoiceRequestDto requestDto) {
        Long productOptionChoiceId = cafeService.addProductOptionChoice(productOptionId, requestDto);

        return new CreateProductOptionChoiceResponseDto(productOptionChoiceId);
    }


    @ApiOperation(value = "상품 옵션 선택지 수정")
    @PutMapping("/api/v1/{productId}/productOption/{productOptionId}/productOptionChoice/{productOptionChoiceId}")
    public UpdateProductOptionChoiceResponseDto updateProductOptionChoice(@PathVariable Long productId,
                                                                          @PathVariable("productOptionId") Long productOptionId,
                                                                          @PathVariable("productOptionChoiceId") Long productOptionChoiceId,
                                                                          @RequestBody @Valid UpdateProductOptionChoiceRequestDto requestDto) {
        cafeService.updateProductOptionChoice(productId, productOptionId, productOptionChoiceId, requestDto);
        ProductOptChoice findProductOptionChoice = cafeService.findProductOptionChoice(productOptionChoiceId);

        return new UpdateProductOptionChoiceResponseDto(findProductOptionChoice.getProductOptionChoiceId(), findProductOptionChoice.getProductOptionChoiceName(), findProductOptionChoice.getProductOptionChoicePrice());
    }


    @ApiOperation(value = "상품 옵션 선택지 삭제")
    @DeleteMapping("/api/v1/{productId}/productOption/{productOptionId}/productOptionChoice/{productOptionChoiceId}")
    public void deleteProductOptionChoice(@PathVariable("productId") Long productId,
                                          @PathVariable("productOptionId") Long productOptionId,
                                          @PathVariable("productOptionChoiceId") Long productOptionChoiceId) {
        cafeService.deleteProductOptionChoice(productId, productOptionId, productOptionChoiceId);
    }
}
