package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.OrderOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderOptionRepository {

    private final EntityManager em;


    /**
     * [OrderOption 추가]
     * OrderOption 엔티티를 데이터베이스에 저장하여 새로운 OrderOption을 추가한다.
     * @param orderOption 추가할 OrderOption 엔티티
     */
    public void create(OrderOption orderOption) {
        em.persist(orderOption);
    }


    /**
     * [OrderOption ID로 조회]
     * 주어진 ID를 기반으로 OrderOption 엔티티를 조회한다.
     * @param orderOptionId 조회할 OrderOption의 ID
     * @return 주어진 ID에 해당하는 OrderOption 엔티티, 없을 경우 null을 반환한다.
     */
    public OrderOption findById(Long orderOptionId) {
        return em.find(OrderOption.class, orderOptionId);
    }


    /**
     * [OrderOption 삭제]
     * 주어진 OrderOption 엔티티를 데이터베이스에서 삭제한다.
     * @param orderOption 삭제할 OrderOption 엔티티
     */
    public void delete(OrderOption orderOption) {
        em.remove(orderOption);
    }


    /**
     * [Cafe별 OrderOption 목록 조회]
     * 주어진 Cafe에 해당하는 모든 OrderOption 엔티티들을 조회한다.
     * @param cafe 조회할 Cafe 엔티티
     * @return 주어진 Cafe에 해당하는 모든 OrderOption 엔티티 리스트, 없을 경우 빈 리스트를 반환한다.
     */
    public List<OrderOption> findByCafe(Cafe cafe) {
        return em.createQuery("select o from OrderOption o join fetch o.cafe where o.cafe = :cafe", OrderOption.class)
                .setParameter("cafe", cafe)
                .getResultList();
    }
}
