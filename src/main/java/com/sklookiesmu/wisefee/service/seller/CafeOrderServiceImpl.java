package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.common.exception.global.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.dto.seller.CreateOrderOptionRequestDto;
import com.sklookiesmu.wisefee.dto.seller.UpdateOrderOptionRequestDto;
import com.sklookiesmu.wisefee.repository.*;
import com.sklookiesmu.wisefee.repository.cafe.CafeRepository;
import com.sklookiesmu.wisefee.repository.order.OrderOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeOrderServiceImpl implements CafeOrderService {

    private final CafeRepository cafeRepository;
    private final OrderOptionRepository orderOptionRepository;

    @Override
    @Transactional
    public Long addOrderOption(Long cafeId, CreateOrderOptionRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = new OrderOption();
        orderOption.setOrderOptionName(requestDto.getOrderOptionName());
        orderOption.setOrderOptionPrice(requestDto.getOrderOptionPrice());
        orderOption.setCafe(cafe);
        orderOptionRepository.create(orderOption);

        return orderOption.getOrderOptionId();
    }


    @Override
    @Transactional
    public void updateOrderOption(Long cafeId, Long orderOptionId, UpdateOrderOptionRequestDto requestDto) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = orderOptionRepository.findById(orderOptionId);

        if (orderOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 주문 옵션입니다.");
        }
        if (!orderOption.getCafe().getCafeId().equals(cafeId)) {
            throw new NoSuchElementFoundException("해당 매장에 속하지 않는 주문 옵션입니다.");
        }

        String newOrderOptionName = requestDto.getOrderOptionName();
        Integer newOrderOptionPrice = requestDto.getOrderOptionPrice();

        if (newOrderOptionName != null) {
            orderOption.setOrderOptionName(newOrderOptionName);
        }

        if (newOrderOptionPrice != null) {
            orderOption.setOrderOptionPrice(newOrderOptionPrice);
        }
    }


    @Override
    @Transactional
    public void deleteOrderOption(Long cafeId, Long orderOptionId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        OrderOption orderOption = orderOptionRepository.findById(orderOptionId);

        if (orderOption == null) {
            throw new NoSuchElementFoundException("존재하지 않는 주문 옵션입니다.");
        }

        if (!orderOption.getCafe().getCafeId().equals(cafeId)) {
            throw new NoSuchElementFoundException("해당 매장에 속하지 않는 주문 옵션입니다.");
        }

        // 주문 옵션 소프트 삭제
        orderOptionRepository.softDelete(orderOption);
    }


    @Override
    public OrderOption findOrderOption(Long orderOptionId) {
        return orderOptionRepository.findById(orderOptionId);
    }


    @Override
    public List<OrderOption> getOrderOptionsByCafeId(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId);

        if (cafe == null) {
            throw new NoSuchElementFoundException("존재하지 않는 매장입니다.");
        }

        return orderOptionRepository.findByCafe(cafe);
    }
}
