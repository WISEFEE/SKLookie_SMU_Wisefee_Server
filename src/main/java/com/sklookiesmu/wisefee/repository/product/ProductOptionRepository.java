package com.sklookiesmu.wisefee.repository.product;

import com.sklookiesmu.wisefee.domain.Product;
import com.sklookiesmu.wisefee.domain.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ProductOptionRepository {

    private final EntityManager em;


    /**
     * [ProductOption 추가]
     * ProductOption 엔티티를 데이터베이스에 저장하여 새로운 ProductOption을 추가한다.
     * @param productOption 추가할 ProductOption 엔티티
     */
    public void create(ProductOption productOption) {
        em.persist(productOption);
    }


    /**
     * [ProductOption ID로 조회]
     * 주어진 ID를 기반으로 ProductOption 엔티티를 조회한다.
     * @param productOptionId 조회할 ProductOption의 ID
     * @return 주어진 ID에 해당하는 ProductOption 엔티티, 없을 경우 null을 반환한다.
     */
    public ProductOption findById(Long productOptionId) {
        return em.find(ProductOption.class, productOptionId);
    }


    /**
     * [ProductOption 삭제]
     * 주어진 ProductOption 엔티티를 데이터베이스에서 삭제한다.
     * @param productOption 삭제할 ProductOption 엔티티
     */
    public void delete(ProductOption productOption) {
        em.remove(productOption);
    }


    /**
     * [Product별 ProductOption 목록 조회]
     * 주어진 Product에 해당하는 모든 ProductOption 엔티티들을 조회한다.
     * @param product 조회할 Product 엔티티
     * @return 주어진 Product에 해당하는 모든 ProductOption 엔티티 리스트, 없을 경우 빈 리스트를 반환한다.
     */
    public List<ProductOption> findByProduct(Product product) {
        return em.createQuery("select po from ProductOption po join fetch po.product where po.product = :product", ProductOption.class)
                .setParameter("product", product)
                .getResultList();
    }

    /**
     * [ProductOption 소프트 삭제로 업데이트]
     * ProductOption 엔티티를 소프트 삭제로 업데이트한다.
     * @param productOption 소프트 삭제할 ProductOption 엔티티
     */
    public void softDelete(ProductOption productOption) {
        productOption.setDeletedAt(LocalDateTime.now());
        em.persist(productOption);
    }
}
