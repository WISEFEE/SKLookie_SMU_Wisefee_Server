package com.sklookiesmu.wisefee.service.seller;

import com.sklookiesmu.wisefee.domain.*;
import com.sklookiesmu.wisefee.dto.seller.*;

import java.util.List;

public interface CafeService {

    /**
     * [매장 추가]
     * 등록된 적이 없는 새로운 매장을 추가한다.
     * @param cafe 매장 엔티티 모델(id=null)
     * @param addrId 매장의 주소 ID
     * @throws IllegalStateException 이미 존재하는 카페일 경우 예외를 던진다.
     */
    Long create(Cafe cafe, Long addrId);


    /**
     * [매장 수정]
     * 매장 ID와 수정할 정보를 기반으로 매장 정보를 업데이트한다.
     * @param cafeId 매장 ID
     * @param requestDto 업데이트할 매장 정보를 담고 있는 DTO 객체
     * @throws IllegalArgumentException 존재하지 않는 매장일 경우 예외를 던진다.
     */
    void update(Long cafeId, UpdateCafeRequestDto requestDto);


    /**
     * [매장 삭제]
     * 매장 ID를 기반으로 매장을 삭제한다.
     * 삭제 시 매장의 주문 옵션과 상품도 함께 삭제된다.
     * @param cafeId 매장 ID
     * @throws IllegalArgumentException 존재하지 않는 매장일 경우 예외를 던집니다.
     */
    void delete(Long cafeId);


    /**
     * [매장 찾기]
     * 매장 ID를 기반으로 매장을 조회한다.
     * @param cafeId 매장 ID
     * @return 조회된 매장 엔티티, 존재하지 않을 경우 null 반환
     */
    Cafe findCafe(Long cafeId);



    /**
     * [모든 소프트 삭제되지 않은 매장 조회]
     * 모든 소프트 삭제되지 않은 매장들을 조회한다.
     * @return 소프트 삭제되지 않은 매장 엔티티 리스트
     */
    List<Cafe> getAllNotDeletedCafes();

}
