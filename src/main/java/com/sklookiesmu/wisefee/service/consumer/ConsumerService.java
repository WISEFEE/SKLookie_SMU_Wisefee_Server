package com.sklookiesmu.wisefee.service.consumer;

import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import com.sklookiesmu.wisefee.dto.consumer.CafeProductDto;
import com.sklookiesmu.wisefee.dto.consumer.OrderOptionDto;
import org.springframework.data.domain.Pageable;

public interface ConsumerService{

    CafeDto.CafeListResponseDto getCafeList(Pageable pageable);
    CafeProductDto.CafeProductListResponseDto getProductList(Long cafeId);

    CafeDto.CafeResponseDto getCafeInfo(Long cafeId);
    OrderOptionDto.OrderOptionResponseDto getOrderOptionInfo(Long cafeId);
}
