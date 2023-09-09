package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.common.exception.AlreadyExistElementException;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthService authService;

    @Transactional
    public Long join(Member member){
        memberRepository.findByEmail(member.getEmail()).ifPresent((existingMember) -> {
            throw new AlreadyExistElementException("이미 존재하는 이메일입니다.");
        });
        member.setAuthType(AuthConstant.AUTH_TYPE_COMMON);
        // 비밀번호 해시 처리
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
        Member member = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("member not found"));
        String authType = member.getAuthType();
        if(!authType.equalsIgnoreCase(AuthConstant.AUTH_TYPE_COMMON)){
            throw new RuntimeException("소셜 로그인 유저는 비밀번호를 수정할 수 없습니다.");
        }
        /* 비밀번호 해시 처리. */
        member.encodePassword(encoder.encode(updateMember.getPassword()));

        return 1L;
    }

    @Transactional
    public Member getMemberByEmail(String email) {
        return memberRepository.
                findByEmail(email).orElseThrow(() -> new NoSuchElementFoundException("member not found"));
    }


    @Transactional
    public Long joinGoogle(Member member, String accessToken) throws IOException {
        String email = authService.getEmailByGoogleToken(accessToken);
        member.setEmail(email);
        member.setPassword(AuthConstant.OAUTH_PASSWORD);
        member.setIsAuthEmail("TRUE");
        member.setAuthType(AuthConstant.AUTH_TYPE_GOOGLE);

        Optional<Member> vailMember = memberRepository.findByEmail(email);
        if(vailMember.isPresent()) {
            throw new AlreadyExistElementException("이미 존재하는 계정입니다." + member.getEmail());
        }

        // 비밀번호 해시 처리
        member.encodePassword(encoder.encode(member.getPassword()));
        memberRepository.create(member);
        return member.getMemberId();
    }


}
