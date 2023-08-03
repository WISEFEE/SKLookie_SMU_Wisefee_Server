package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ConsumerCafeService {

    /**
     * [매장 리스트 조회]
     * 매장 리스트를 조회하고, 이름순으로 조회 가능하도록 한다.
     * @param pageable
     * @return [매장 리스트 반환]
     */
    CafeDto.CafeListResponseDto getCafeList(Pageable pageable);

    /**
     * [매장의 음료 제품 리스트 조회]
     * 매장의 PK를 통해서 매장 음료 리스트를 조회한다.
     * @param cafeId
     * @return [음료 제품 리스트 반환]
     */
    ProductDto.ProductListResponseDto getProductList(Long cafeId);


    /**
     * [매장 정보 조회]
     * 특정 매장 정보를 조회한다.
     * @param cafeId
     * @return [매장 정보 DTO 반환]
     */
    CafeDto.CafeResponseDto getCafeInfo(Long cafeId);
}
