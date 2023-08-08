package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import com.sklookiesmu.wisefee.dto.seller.*;

import java.util.List;

public interface CafeProductService {

    /**
     * [상품 추가]
     * 매장 ID와 상품을 생성하기 위한 정보를 입력받아 상품을 추가한다.
     * @param cafeId 매장 ID
     * @param requestDto 생성할 상품 정보를 담고 있는 DTO 객체
     * @return 생성된 상품의 PK 반환
     * @throws IllegalArgumentException 존재하지 않는 매장일 경우 예외를 던진다.
     */
    Long addProduct(Long cafeId, CreateProductRequestDto requestDto);


    /**
     * [상품 수정]
     * 매장 ID와 상품 ID, 수정할 상품 정보를 기반으로 상품을 업데이트한다.
     * @param cafeId 매장 ID
     * @param productId 상품 ID
     * @param requestDto 업데이트할 상품 정보를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 존재하지 않는 매장 또는 상품일 경우, 해당 매장에 속하지 않는 상품인 경우 예외를 던진다.
     */
    void updateProduct(Long cafeId, Long productId, UpdateProductRequestDto requestDto);


    /**
     * [상품 삭제]
     * 매장 ID와 상품 ID를 기반으로 상품을 삭제한다.
     * 삭제 시 상품에 속한 상품 옵션도 함께 삭제된다.
     * @param cafeId 매장 ID
     * @param productId 상품 ID
     * @throws IllegalArgumentException 존재하지 않는 매장 또는 상품일 경우, 해당 매장에 속하지 않는 상품인 경우 예외를 던진다.
     */
    void deleteProduct(Long cafeId, Long productId);


    /**
     * [상품 옵션 추가]
     * 상품 ID와 생성할 상품 옵션 정보를 입력받아 상품 옵션을 추가한다.
     * @param productId 상품 ID
     * @param requestDto 생성할 상품 옵션 정보를 담고 있는 DTO 객체
     * @return 생성된 상품 옵션의 PK 반환
     * @throws IllegalArgumentException 존재하지 않는 상품일 경우 예외를 던진다.
     */
    Long addProductOption(Long productId, CreateProductOptionRequestDto requestDto);


    /**
     * [상품 옵션 수정]
     * 상품 ID와 상품 옵션 ID, 수정할 상품 옵션 정보를 기반으로 상품 옵션을 업데이트한다.
     * @param productId 상품 ID
     * @param productOptionId 상품 옵션 ID
     * @param requestDto 업데이트할 상품 옵션 정보를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 존재하지 않는 상품 또는 상품 옵션일 경우, 해당 상품에 속하지 않는 상품 옵션인 경우 예외를 던진다.
     */
    void updateProductOption(Long productId, Long productOptionId, UpdateProductOptionRequestDto requestDto);


    /**
     * [상품 옵션 삭제]
     * 상품 ID와 상품 옵션 ID를 기반으로 상품 옵션을 삭제한다.
     * 삭제 시 상품 옵션에 속한 상품 옵션 선택지도 함께 삭제된다.
     * @param productId 상품 ID
     * @param productOptionId 상품 옵션 ID
     * @throws IllegalArgumentException 존재하지 않는 상품 또는 상품 옵션일 경우, 해당 상품에 속하지 않는 상품 옵션인 경우 예외를 던진다.
     */
    void deleteProductOption(Long productId, Long productOptionId);


    /**
     * [상품 옵션 선택지 추가]
     * 상품 옵션 ID와 생성할 상품 옵션 선택지 정보를 입력받아 상품 옵션 선택지를 추가한다.
     * @param productOptionId 상품 옵션 ID
     * @param requestDto 생성할 상품 옵션 선택지 정보를 담고 있는 DTO 객체
     * @return 생성된 상품 옵션 선택지의 PK 반환
     * @throws IllegalArgumentException 존재하지 않는 상품 옵션일 경우 예외를 던진다.
     */
    Long addProductOptionChoice(Long productOptionId, CreateProductOptionChoiceRequestDto requestDto);


    /**
     * [상품 옵션 선택지 수정]
     * 상품 ID, 상품 옵션 ID, 상품 옵션 선택지 ID, 수정할 상품 옵션 선택지 정보를 기반으로 상품 옵션 선택지를 업데이트한다.
     * @param productId 상품 ID
     * @param productOptionId 상품 옵션 ID
     * @param productOptionChoiceId 상품 옵션 선택지 ID
     * @param requestDto 업데이트할 상품 옵션 선택지 정보를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 존재하지 않는 상품 또는 상품 옵션 또는 상품 옵션 선택지일 경우, 해당 상품 옵션에 속하지 않는 상품 옵션 선택지인 경우 예외를 던진다.
     */
    void updateProductOptionChoice(Long productId, Long productOptionId, Long productOptionChoiceId, UpdateProductOptionChoiceRequestDto requestDto);


    /**
     * [상품 옵션 선택지 삭제]
     * 상품 ID, 상품 옵션 ID, 상품 옵션 선택지 ID를 기반으로 상품 옵션 선택지를 삭제한다.
     * @param productId 상품 ID
     * @param productOptionId 상품 옵션 ID
     * @param productOptionChoiceId 상품 옵션 선택지 ID
     * @throws IllegalArgumentException 존재하지 않는 상품 또는 상품 옵션 또는 상품 옵션 선택지일 경우, 해당 상품 옵션에 속하지 않는 상품 옵션 선택지인 경우 예외를 던진다.
     */
    void deleteProductOptionChoice(Long productId, Long productOptionId, Long productOptionChoiceId);


    /**
     * [상품 찾기]
     * 상품 ID를 기반으로 상품을 조회한다.
     * @param productId 상품 ID
     * @return 조회된 상품 엔티티, 존재하지 않을 경우 null 반환
     */
    Product findProduct(Long productId);


    /**
     * [상품 옵션 찾기]
     * 상품 옵션 ID를 기반으로 상품 옵션을 조회한다.
     * @param productOptionId 상품 옵션 ID
     * @return 조회된 상품 옵션 엔티티, 존재하지 않을 경우 null 반환
     */
    ProductOption findProductOption(Long productOptionId);


    /**
     * [상품 옵션 선택지 찾기]
     * 상품 옵션 선택지 ID를 기반으로 상품 옵션 선택지를 조회한다.
     * @param productOptionChoiceId 상품 옵션 선택지 ID
     * @return 조회된 상품 옵션 선택지 엔티티, 존재하지 않을 경우 null 반환
     */
    ProductOptChoice findProductOptionChoice(Long productOptionChoiceId);


    /**
     * [상품 리스트 조회]
     * 매장 ID를 기반으로 해당 매장의 상품 리스트를 조회한다.
     * @return 해당 매장의 상품 리스트
     */
    List<Product> getProductsByCafeId(Long cafeId);

}
