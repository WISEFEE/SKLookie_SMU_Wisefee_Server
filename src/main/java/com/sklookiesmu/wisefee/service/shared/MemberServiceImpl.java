package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.common.exception.AlreadyExistElementException;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(Member member){
        memberRepository.findByEmail(member.getEmail()).ifPresent((existingMember) -> {
            throw new AlreadyExistElementException("member already exist");
        });
        member.encodePassword(encoder.encode(member.getPassword()));
        return memberRepository.create(member);
    }

    @Transactional
    public List<Member> getMembers(String order){
        return memberRepository.findAll(order)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NoSuchElementFoundException("members not found"));
    }

    @Transactional
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("member not found"));
    }

    @Transactional
    public Long updateMember(Long id, Member updateMember) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("member not found"))
                .updateMember(updateMember);
    }

    @Transactional
    public Long updatePasswordAsMember(Long id, Member updateMember) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("member not found"))
                .encodePassword(encoder.encode(updateMember.getPassword()));
    }

    @Transactional
    public Member getMemberByEmail(String email) {
        return memberRepository.
                findByEmail(email).orElseThrow(() -> new NoSuchElementFoundException("member not found"));
    }

}
