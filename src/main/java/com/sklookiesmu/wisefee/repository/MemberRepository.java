package com.sklookiesmu.wisefee.repository;

import com.sklookiesmu.wisefee.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;




@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    /**
     * [#PHS1 Member 엔티티 전체조회]
     * 회원 리스트 조회
     * @param [order 정렬 순서(asc,desc)]
     * @return [Member 엔티티 리스트]
     *
     */
    public List<Member> findAll(String order) {
        /* JPQL */
//        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//        return result;

        /* JPA Criteria */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);
        Root<Member> root = query.from(Member.class);
        query.select(root);
        if(order.equals("desc")){
            query.orderBy(cb.desc(root.get("id")));
        }


        TypedQuery<Member> typedQuery = em.createQuery(query);
        List<Member> result = typedQuery.getResultList();

        return result;
    }


    /**
     * [Member 엔티티 단일조회]
     * 회원 조회.
     * @param [id PK]
     * @return [Member 엔티티]
     */
    public Member find(Long id){
        Member result = em.find(Member.class, id);
        return result;
    }


    /**
     * [Member 엔티티 단일조회]
     * 회원 조회
     * @param [Member Member 엔티티(pk=null)]
     */
    public void create(Member member){
        em.persist(member);
    }


}


