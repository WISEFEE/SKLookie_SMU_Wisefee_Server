package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        memberRepository.create(member);
        return member.getId();
    }

    @Transactional
    public List<Member> getMembers(String order){
        List<Member> members = memberRepository.findAll(order);
        return members;
    }

    @Transactional
    public Member getMember(Long id) {
        Member member = memberRepository.find(id);
        /* 강제 예외처리 예시 */

        if(id == 1){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "강제 예외 처리");
            //throw new IllegalStateException("Forbidden");
        }

        return member;
    }
}
