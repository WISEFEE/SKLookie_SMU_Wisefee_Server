package com.sklookiesmu.wisefee.repository.product;

import com.sklookiesmu.wisefee.domain.ProductOptChoice;
import com.sklookiesmu.wisefee.domain.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductOptChoiceRepository {

    private final EntityManager em;


    /**
     * [ProductOptChoice 추가]
     * ProductOptChoice 엔티티를 데이터베이스에 저장하여 새로운 ProductOptChoice을 추가한다.
     * @param productOptChoice 추가할 ProductOptChoice 엔티티
     */
    public void create(ProductOptChoice productOptChoice) {
        em.persist(productOptChoice);
    }


    /**
     * [ProductOptChoice ID로 조회]
     * 주어진 ID를 기반으로 ProductOptChoice 엔티티를 조회한다.
     * @param productOptChoiceId 조회할 ProductOptChoice의 ID
     * @return 주어진 ID에 해당하는 ProductOptChoice 엔티티, 없을 경우 null을 반환한다.
     */
    public ProductOptChoice findById(Long productOptChoiceId) {
        return em.find(ProductOptChoice.class, productOptChoiceId);
    }


    /**
     * [ProductOptChoice 삭제]
     * 주어진 ProductOptChoice 엔티티를 데이터베이스에서 삭제한다.
     * @param productOptChoice 삭제할 ProductOptChoice 엔티티
     */
    public void delete(ProductOptChoice productOptChoice) {
        em.remove(productOptChoice);
    }


    /**
     * [ProductOption별 ProductOptChoice 목록 조회]
     * 주어진 ProductOption에 해당하는 모든 ProductOptChoice 엔티티들을 조회한다.
     * @param productOption 조회할 ProductOption 엔티티
     * @return 주어진 ProductOption에 해당하는 모든 ProductOptChoice 엔티티 리스트, 없을 경우 빈 리스트를 반환힌다.
     */
    public List<ProductOptChoice> findByProductOption(ProductOption productOption) {
        return em.createQuery("select poc from ProductOptChoice poc join fetch poc.productOption where poc.productOption = :productOption", ProductOptChoice.class )
                .setParameter("productOption", productOption)
                .getResultList();
    }

}
