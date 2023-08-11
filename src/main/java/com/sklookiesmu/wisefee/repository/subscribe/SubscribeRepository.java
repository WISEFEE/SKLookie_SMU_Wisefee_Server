package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscribeRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Subscribe> findAllByCafeId(Long cafeId) {
        return em.createQuery("select s from Subscribe s where s.cafe.cafeId = :cafeId", Subscribe.class)
                .setParameter("cafeId", cafeId)
                .getResultList();
    }
}
