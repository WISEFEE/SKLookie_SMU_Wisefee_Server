package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeRepository {


    private final EntityManager em;


    /**
     * [Cafe 추가]
     * Cafe 엔티티를 데이터베이스에 저장하여 새로운 Cafe를 추가한다.
     * @param cafe 추가할 Cafe 엔티티
     */
    public void create(Cafe cafe) {
        em.persist(cafe);
    }


    /**
     * [Cafe ID로 조회]
     * 주어진 ID를 기반으로 Cafe 엔티티를 조회한다.
     * @param id 조회할 Cafe의 ID
     * @return 주어진 ID에 해당하는 Cafe 엔티티, 없을 경우 null을 반환한다.
     */
    public Cafe findById(Long id) {
        return em.find(Cafe.class, id);
    }


    /**
     * [Cafe 제목으로 조회]
     * 주어진 제목을 기반으로 Cafe 엔티티들 중에서 제목이 일치하는 Cafe들을 조회한다.
     * @param title 조회할 Cafe의 제목
     * @return 주어진 제목과 일치하는 Cafe 엔티티 리스트, 일치하는 Cafe가 없을 경우 빈 리스트를 반환한다.
     */
    public List<Cafe> findByTitle(String title) {
        return em.createQuery("select c from Cafe c where c.title = :title", Cafe.class)
                .setParameter("title", title)
                .getResultList();
    }


    /**
     * [Cafe 삭제]
     * 주어진 Cafe 엔티티를 데이터베이스에서 삭제한다.
     * @param cafe 삭제할 Cafe 엔티티
     */
    public void delete(Cafe cafe) {
        em.remove(cafe);
    }


    /**
     * [소프트 삭제되지 않은 모든 Cafe 조회]
     * 소프트 삭제되지 않은 모든 Cafe 엔티티들을 조회한다.
     * @return 소프트 삭제되지 않은 모든 Cafe 엔티티 리스트
     */
    public List<Cafe> findAllNotDeleted() {
        return em.createQuery("select c from Cafe c where c.deletedAt is null", Cafe.class)
                .getResultList();
    }


    /**
     * [Cafe 소프트 삭제로 업데이트]
     * Cafe 엔티티를 소프트 삭제로 업데이트한다.
     * @param cafe 소프트 삭제할 Cafe 엔티티
     */
    public void softDelete(Cafe cafe) {
        cafe.setDeletedAt(LocalDateTime.now());
        em.persist(cafe);
    }
}
