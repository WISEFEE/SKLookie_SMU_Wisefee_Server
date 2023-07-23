package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Member;

import java.util.List;

public interface MemberService {

    /**
     * [회원 가입]
     * 회원 정보를 입력받아, 회원가입시킨다.
     * @param [member 회원 엔티티 모델(id=null)]
     * @return [성공 시 회원 PK 반환]
     * @throws [예외 타입1] [예외 설명]
     */
    public abstract Long join(Member member);


    /**
     * [회원 리스트 조회]
     * 회원 전체 리스트를 조회한다.
     * @param [order 정렬 순서(asc/desc)]
     * @return [회원 전체 리스트 반환]
     */
    public abstract List<Member> getMembers(String order);


    /**
     * [회원 조회]
     * 회원을 PK를 통해서 조회한다.
     * @param [id 회원 엔티티 PK]
     * @return [회원 반환]
     */
    public abstract Member getMember(Long id);


}