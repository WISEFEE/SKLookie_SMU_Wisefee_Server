package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.error.MemberNotFoundException;
import com.sklookiesmu.wisefee.common.error.ValidateMemberException;
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
        Optional<Member> vailMember = memberRepository.findByEmail(member.getEmail());
        if(vailMember.isPresent()) {
            throw new ValidateMemberException("이미 존재하는 이메일입니다." + member.getEmail());
        }
        member.setAuthType(AuthConstant.AUTH_TYPE_COMMON);
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
            throw new MemberNotFoundException("회원을 찾을 수 없습니다");
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
    public Long updateMember(Long id, Member updateMember) {
        Member member = memberRepository.find(id);

        /* 예외 처리 */
        if(member == null) {
            throw new MemberNotFoundException("Member not found with email " + id);
        }

        return member.updateMember(updateMember);

    }

    @Transactional
    public Long updatePasswordAsMember(Long id, Member updateMember) {
        Member member = memberRepository.find(id);
        String authType = member.getAuthType();
        if(!authType.equalsIgnoreCase(AuthConstant.AUTH_TYPE_COMMON)){
            throw new RuntimeException("소셜 로그인 유저는 비밀번호를 수정할 수 없습니다.");
        }
        /* 비밀번호 해시 처리. */
        member.encodePassword(encoder.encode(updateMember.getPassword()));

        return 1L;
    }

    @Transactional
    public Optional<Member> getMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        /* 예외 처리 */
        if(member.isEmpty()) {
            throw new MemberNotFoundException("해당 이메일로 회원을 찾을 수 없습니다 " + email);
        }

        return member;
    }



    @Transactional
    public Long joinGoogle(Member member, String accessToken) throws IOException {
        String email = authService.verifyGoogleToken(accessToken);
        member.setEmail(email);
        member.setPassword(AuthConstant.OAUTH_PASSWORD);
        member.setIsAuthEmail("TRUE");
        member.setAuthType(AuthConstant.AUTH_TYPE_GOOGLE);

        Optional<Member> vailMember = memberRepository.findByEmail(email);
        if(vailMember.isPresent()) {
            throw new ValidateMemberException("이미 존재하는 계정입니다." + member.getEmail());
        }

        // 비밀번호 해시 처리
        member.encodePassword(encoder.encode(member.getPassword()));
        memberRepository.create(member);
        return member.getMemberId();
    }


}
