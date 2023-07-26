package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.CafeProductDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.OrderOptionRepository;
import com.sklookiesmu.wisefee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final CafeRepository cafeRepository;
    private final ProductRepository productRepository;
    private final OrderOptionRepository orderOptionRepository;

    @Override
    public CafeDto.CafeListResponseDto getCafeList(Pageable pageable){
        Slice<Cafe> cafeList = cafeRepository.findWithNameFilter(pageable);
        return CafeDto.CafeListResponseDto.from(cafeList);
    }

    /**
     * 매장 상세보기 -> 음료 리스트
     */
    @Override
    public CafeProductDto.CafeProductListResponseDto getProductList(Long cafeId){
        List<Product> productList = productRepository.findAll();
        return CafeProductDto.CafeProductListResponseDto.of(productList);
    }

    /**
     * 매장 상세보기 -> 매장 정보
     */
    @Override
    public CafeDto.CafeResponseDto getCafeInfo(Long cafeId){
        Cafe cafeDetails = cafeRepository.findById(cafeId).orElseThrow();
        return CafeDto.CafeResponseDto.from(cafeDetails);
    }

    /**
     * 매장 상세보기 -> 주문 옵션 정보
     */
    @Override
    public OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId){
        OrderOption orderOption = orderOptionRepository.findOptionByCafeId(cafeId).orElseThrow();
        return OrderOptionDto.OrderOptionResponseDto.from(orderOption);
    }

    /**
     * 정기구독 체결
     */
}
