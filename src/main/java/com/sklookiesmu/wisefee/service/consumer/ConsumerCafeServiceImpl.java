package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.ProductDto;
import com.sklookiesmu.wisefee.repository.cafe.CafeJpaRepository;
import com.sklookiesmu.wisefee.repository.product.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerCafeServiceImpl implements ConsumerCafeService{

    private final ProductJpaRepository productRepository;
    private final CafeJpaRepository cafeJpaRepository;


    @Override
    public CafeDto.CafeListResponseDto getCafeList(Pageable pageable){
        Slice<Cafe> cafeList = cafeJpaRepository.findWithNameFilter(pageable);
        return CafeDto.CafeListResponseDto.from(cafeList);
    }

    /**
     * 매장 상세보기 -> 음료 리스트
     */
    @Override
    public ProductDto.ProductListResponseDto getProductList(Long cafeId){

        List<Product> productList = productRepository.findAllByCafeId(cafeId);

        return ProductDto.ProductListResponseDto.of(productList);
    }

    /**
     * 매장 상세보기 -> 매장 정보
     */
    @Override
    public CafeDto.CafeResponseDto getCafeInfo(Long cafeId){
        Cafe cafeDetails = cafeJpaRepository.findById(cafeId).orElseThrow();
        return CafeDto.CafeResponseDto.from(cafeDetails);
    }
}
