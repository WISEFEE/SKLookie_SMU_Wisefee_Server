package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.error.MemberNotFoundException;
import com.sklookiesmu.wisefee.common.error.ValidateMemberException;
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
        Optional<Member> vailMember = memberRepository.findByEmail(member.getEmail());
        if(vailMember.isPresent()) {
            throw new ValidateMemberException("This member email is already exist. " + member.getEmail());
        }
        // 비밀번호 해시 처리
        member.encodePassword(encoder.encode(member.getPassword()));
        memberRepository.create(member);
        return member.getMemberId();
    }

    @Transactional
    public List<Member> getMembers(String order){
        List<Member> members = memberRepository.findAll(order);

        /* 예외 처리 */
        if(members.isEmpty()){
            throw new MemberNotFoundException("Any member doesn't exists.");
        }

        return members;
    }
    @Transactional
    public Member getMember(Long id) {
        Member member = memberRepository.find(id);

//        /* 강제 예외처리 예시 */
//        if(id == 1){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "강제 예외 처리");
//            //throw new IllegalStateException("Forbidden");
//        }

        return member;
    }

    @Transactional
    public Long updateMember(String email, Member updateMember) {
        Optional<Member> member = memberRepository.findByEmail(email);

        /* 예외 처리 */
        if(member.isEmpty()) {
            throw new MemberNotFoundException("Member not found with email " + email);
        }

        /* 비밀번호 해시 처리. */
        updateMember.encodePassword(encoder.encode(updateMember.getPassword()));
        return member.get().updateMember(updateMember);

    }

    @Transactional
    public Optional<Member> getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        /* 예외 처리 */
        if(member.isEmpty()) {
            throw new MemberNotFoundException("Member not found with email " + email);
        }

        return member;
    }


}
