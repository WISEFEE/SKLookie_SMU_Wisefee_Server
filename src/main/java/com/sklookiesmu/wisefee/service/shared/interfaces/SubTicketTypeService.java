package com.sklookiesmu.wisefee.service.shared.interfaces;
import com.sklookiesmu.wisefee.domain.SubTicketType;

import java.util.List;

public interface SubTicketTypeService {

    /**
     * [구독권 유형 추가]
     * 구독권 유형 정보를 입력받아 데이터베이스에 저장한다.
     * @param [member 구독권 유형 엔티티 모델(id=null)]
     * @return [성공 시 구독권 유형 PK 반환]
     */
    public abstract Long addTicketType(SubTicketType ticket);


    /**
     * [구독권 유형 ID로 검색]
     * 구독권 유형 ID를 기반으로 검색한다.
     * @param [member 구독권 유형 ID(PK)]
     * @return [성공 시 SubTicketType 반환]
     */
    public abstract SubTicketType selectById(Long id);

    /**
     * [구독권 유형 리스트 검색]
     * 구독권 유형 리스트를 조회한다
     * @return [성공 시 SubTicketType[] 반환]
     */
    public abstract List<SubTicketType> selectList();



}