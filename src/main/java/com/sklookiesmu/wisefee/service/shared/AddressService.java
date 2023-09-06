package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.Member;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    /**
     * [주소 추가]
     * 주소 정보를 입력받아 데이터베이스에 저장한다.
     * @param [member 주소 엔티티 모델(id=null)]
     * @return [성공 시 주소 PK 반환]
     */
    public abstract Long addAddress(Address address);


    /**
     * [주소 ID로 검색]
     * 주소 ID를 기반으로 검색한다.
     * @param [member 주소 ID(PK)]
     * @return [성공 시 Address 반환]
     */
    public abstract Address getAddressById(Long id);



}