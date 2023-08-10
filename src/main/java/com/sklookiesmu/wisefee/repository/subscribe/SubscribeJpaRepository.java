package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeJpaRepository extends JpaRepository<Subscribe, Long> {

    @Query(value = "select s from Subscribe s where s.member.memberId = :memberId")
    List<Subscribe> findAllByMemberId(@Param(value = "memberId") Long memberId);

    Optional<Subscribe> findByMemberAndSubStatus(Member member, String subStatus);
}
