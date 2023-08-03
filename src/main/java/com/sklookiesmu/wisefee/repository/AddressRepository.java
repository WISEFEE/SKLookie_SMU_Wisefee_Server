package com.sklookiesmu.wisefee.repository;
import com.sklookiesmu.wisefee.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AddressRepository {
    private final EntityManager em;



    /**
     * [Address 엔티티 추가]
     * 주소 추가
     * @param [Address Address 엔티티(pk=null)]
     */
    public void create(Address address){
        em.persist(address);
    }


    /**
     * [Address 엔티티 단일조회]
     * 주소 조회.
     * @param [id PK]
     * @return [Address 엔티티]
     */
    public Address find(Long id){
        Address result = em.createQuery("select a from Address a where a.id = :id and a.deletedAt IS NULL", Address.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }


}
