package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.dto.seller.CreateOrderOptionRequestDto;
import com.sklookiesmu.wisefee.dto.seller.UpdateOrderOptionRequestDto;

public interface CafeOrderService {

    /**
     * [주문 옵션 추가]
     * 매장 ID와 주문 옵션을 생성하기 위한 정보를 입력받아 주문 옵션을 추가한다.
     * @param cafeId 매장 ID
     * @param requestDto 생성할 주문 옵션 정보를 담고 있는 DTO 객체
     * @return 생성된 주문 옵션의 PK 반환
     * @throws IllegalArgumentException 존재하지 않는 매장일 경우 예외를 던진다.
     */
    Long addOrderOption(Long cafeId, CreateOrderOptionRequestDto requestDto);


    /**
     * [주문 옵션 수정]
     * 매장 ID와 주문 옵션 ID, 수정할 주문 옵션 정보를 기반으로 주문 옵션을 업데이트한다.
     * @param cafeId 매장 ID
     * @param orderOptionId 주문 옵션 ID
     * @param requestDto 업데이트할 주문 옵션 정보를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 존재하지 않는 매장 또는 주문 옵션일 경우, 해당 매장에 속하지 않는 주문 옵션인 경우 예외를 던진다.
     */
    void updateOrderOption(Long cafeId, Long orderOptionId, UpdateOrderOptionRequestDto requestDto);


    /**
     * [주문 옵션 삭제]
     * 매장 ID와 주문 옵션 ID를 기반으로 주문 옵션을 삭제한다.
     * @param cafeId 매장 ID
     * @param orderOptionId 주문 옵션 ID
     * @throws IllegalArgumentException 존재하지 않는 매장 또는 주문 옵션일 경우, 해당 매장에 속하지 않는 주문 옵션인 경우 예외를 던진다.
     */
    void deleteOrderOption(Long cafeId, Long orderOptionId);


    /**
     * [주문 옵션 찾기]
     * 주문 옵션 ID를 기반으로 주문 옵션을 조회한다.
     * @param orderOptionId 주문 옵션 ID
     * @return 조회된 주문 옵션 엔티티, 존재하지 않을 경우 null 반환
     */
    OrderOption findOrderOption(Long orderOptionId);

}
