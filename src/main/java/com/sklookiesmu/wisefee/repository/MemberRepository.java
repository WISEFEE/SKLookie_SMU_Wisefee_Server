package com.sklookiesmu.wisefee.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;


    /**
     * [#PHS1 Member 엔티티 전체조회]
     * 회원 리스트 조회
     *
     * @param [order 정렬 순서(asc,desc)]
     * @return [Member 엔티티 리스트]
     */
    public Optional<List<Member>> findAll(String order) {
        QMember qMember = QMember.member;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Optional<List<Member>> result = Optional.ofNullable(queryFactory.selectFrom(qMember).fetch());

        if(order.equals("desc")){
            result = Optional.ofNullable(queryFactory.selectFrom(qMember).orderBy(qMember.memberId.desc()).fetch());
        }
        return result;
    }


    /**
     * [Member 엔티티 단일조회]
     * 회원 조회.
     *
     * @param [id PK]
     * @return [Member 엔티티]
     */
    public Optional<Member> findById(Long id){
        Optional<Member> result = Optional.ofNullable(em.find(Member.class, id));
        return result;
    }


    /**
     * [Member 엔티티 추가]
     * 회원 추가
     * @param [Member Member 엔티티(pk=null)]
     */
    public Long create(Member member){
        em.persist(member);
        return member.getMemberId();
    }
    


    /**
     * [Member 엔티티 단일조회 by Email]
     * 로그인 시, 이메일로 회원 조회.
     * @param [email 이메일]
     * @return [Member || Null]
     */
    public Optional<Member> findByEmail(String email){
        TypedQuery<Member> typedQuery = em.createQuery("select m from Member m where m.email = :email", Member.class);
        typedQuery.setParameter("email", email);
        try{
            Member member = (Member) typedQuery.getSingleResult();
            return Optional.ofNullable(member);
        } catch (NoResultException e){
               return Optional.empty();
        }
    }

}


