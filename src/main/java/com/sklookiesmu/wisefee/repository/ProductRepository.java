package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.OrderOption;
import com.sklookiesmu.wisefee.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;


    /**
     * [Product 추가]
     * Product 엔티티를 데이터베이스에 저장하여 새로운 Product를 추가한다.
     * @param product 추가할 Product 엔티티
     */
    public void create(Product product) {
        em.persist(product);
    }


    /**
     * [Product ID로 조회]
     * 주어진 ID를 기반으로 Product 엔티티를 조회한다.
     * @param productId 조회할 Product의 ID
     * @return 주어진 ID에 해당하는 Product 엔티티, 없을 경우 null을 반환한다.
     */
    public Product findById(Long productId) {
        return em.find(Product.class, productId);
    }


    /**
     * [Product 삭제]
     * 주어진 Product 엔티티를 데이터베이스에서 삭제한다.
     * @param product 삭제할 Product 엔티티
     */
    public void delete(Product product) {
        em.remove(product);
    }


    /**
     * [Cafe별 Product 목록 조회]
     * 주어진 Cafe에 해당하는 모든 Product 엔티티들을 조회한다.
     * @param cafe 조회할 Cafe 엔티티
     * @return 주어진 Cafe에 해당하는 모든 Product 엔티티 리스트, 없을 경우 빈 리스트를 반환한다.
     */
    public List<Product> findByCafe(Cafe cafe) {
        return em.createQuery("select p from Product p join fetch p.cafe where p.cafe = :cafe", Product.class)
                .setParameter("cafe", cafe)
                .getResultList();
    }


    /**
     * [Product 소프트 삭제로 업데이트]
     * Product 엔티티를 소프트 삭제로 업데이트한다.
     * @param product 소프트 삭제할 Product 엔티티
     */
    public void softDelete(Product product) {
        product.setDeletedAt(LocalDateTime.now());
        em.persist(product);
    }
}
