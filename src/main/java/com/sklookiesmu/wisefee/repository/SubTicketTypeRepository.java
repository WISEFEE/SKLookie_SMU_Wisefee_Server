package com.sklookiesmu.wisefee.repository;
import com.sklookiesmu.wisefee.domain.Address;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubTicketTypeRepository {
    private final EntityManager em;



    /**
     * [SubTicketType 엔티티 추가]
     * 구독권 유형 추가
     * @param [SubTicketType SubTicketType 엔티티(pk=null)]
     */
    public void create(SubTicketType ticket){
        em.persist(ticket);
    }


    /**
     * [SubTicketType 엔티티 단일조회]
     * 구독권 유형 조회.
     * @param [id PK]
     * @return [SubTicketType 엔티티]
     */
    public SubTicketType find(Long id){
        SubTicketType result = em.createQuery("select s from SubTicketType s where s.id = :id and s.deletedAt IS NULL", SubTicketType.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }



    /**
     * [SubTicketType 엔티티 리스트조회]
     * 구독권 유형 리스트 조회.
     * @return [SubTicketType[] 엔티티]
     */
    public List<SubTicketType> findList(){
        List<SubTicketType> result = em.createQuery("select s from SubTicketType s where s.deletedAt IS NULL", SubTicketType.class).getResultList();
        return result;
    }




}
