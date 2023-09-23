package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.enums.member.AccountType;
import com.sklookiesmu.wisefee.common.exception.NoSuchElementFoundException;
import com.sklookiesmu.wisefee.common.exception.AlreadyExistElementException;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.service.shared.interfaces.AuthService;
import com.sklookiesmu.wisefee.service.shared.interfaces.CartService;
import com.sklookiesmu.wisefee.service.shared.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthService authService;
    private final CartService cartService;

    @Transactional
    public Long join(Member member){
        if(memberRepository.existsByEmail(member.getEmail())) {
            throw new AlreadyExistElementException("이미 존재하는 이메일입니다.");
        }

        member.setDefaultAuthType(AuthConstant.AUTH_TYPE_COMMON);
        member.encodePassword(encoder.encode(member.getPassword()));
        memberRepository.save(member);

        if (member.getAccountType().equals(AccountType.CONSUMER)) {
            cartService.addCart(member.getMemberId());
        }
        return member.getMemberId();
    }

    @Transactional
    public List<Member> getMembers(String order){
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()) {
            throw new NoSuchElementFoundException("회원이 존재하지 않습니다.");
        }
        return members;
    }

    @Transactional
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("회원이 존재하지 않습니다."));
    }

    @Transactional
    public Long updateMember(Long id, Member updateMember) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("회원이 존재하지 않습니다."))
                .updateMember(updateMember);
    }

    @Transactional
    public Long updatePasswordAsMember(Long id, Member updateMember) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("회원이 존재하지 않습니다."));

        if(member.getAuthType() != AuthConstant.AUTH_TYPE_COMMON){
            throw new UnsupportedOperationException("소셜 로그인 유저는 비밀번호를 수정할 수 없습니다.");
        }
        return member.encodePassword(encoder.encode(updateMember.getPassword()));
    }

    @Transactional
    public Member getMemberByEmail(String email) {
        return memberRepository.
                findByEmail(email).orElseThrow(() -> new NoSuchElementFoundException("회원이 존재하지 않습니다."));
    }


    @Transactional
    public Long joinGoogle(Member member, String accessToken) throws IOException {
        String email = authService.getEmailByGoogleToken(accessToken);

        if(memberRepository.existsByEmail(email)) {
            throw new AlreadyExistElementException("이미 존재하는 계정입니다." + member.getEmail());
        }

        member.joinOAuth(email, AuthConstant.OAUTH_PASSWORD, true, AuthConstant.AUTH_TYPE_GOOGLE);
        member.encodePassword(encoder.encode(member.getPassword()));

        memberRepository.save(member);

        if (member.getAccountType().equals(AccountType.CONSUMER)) {
            cartService.addCart(member.getMemberId());
        }
        return member.getMemberId();
    }

    public void validateCurrentUser(Long id) {
        if(!(id.equals(SecurityUtil.getCurrentMemberPk())))
            throw new AlreadyExistElementException("로그인한 사용자의 아이디가 아닙니다.");
    }

}
