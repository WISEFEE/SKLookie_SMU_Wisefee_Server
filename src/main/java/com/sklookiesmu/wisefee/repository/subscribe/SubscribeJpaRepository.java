package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.Cafe;
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
    @Query(value = "select s from Subscribe s where s.member.memberId = :memberId and s.subId = :subId")
    Optional<Subscribe> findByMemberIdAndSubscribeId(@Param(value = "memberId") Long memberId,
                                                     @Param(value = "subId") Long subId);

    Optional<Subscribe> findByMemberAndCafe(Member member, Cafe cafe);

    @Query(value = "select s from Subscribe s where s.subId = :subId and s.cafe.cafeId = :cafeId")
    Optional<Subscribe> findByIdAndCafeId(@Param(value = "subId") Long subId,
                                          @Param(value = "cafeId") Long cafeId);
    @Query(value = "select s from Subscribe s where s.member.memberId = :memberId and s.cafe.cafeId = :cafeId")
    Subscribe findByCafeIdAndMemberId(@Param(value = "memberId") Long memberId,
                                      @Param(value = "cafeId") Long cafeId);

}
