package com.sklookiesmu.wisefee.common.auth.custom;

import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;



/**
 * [JWT 인증 커스텀 필터에서 사용될 재정의된 UserDetailsService]
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    /**
     * [JWT 토큰의 인증 계정 찾기]
     * UserDetailsService의 메서드를 오버라이딩하여, MemberRepository의 로직으로 인증 계정을 찾음
     */
    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    /**
     * [CustomUserDetail 형태로 반환]
     * 해당 계정 정보가 존재 시, CustomUserDetail의 형태로 리턴
     * @param [Member 찾은 멤버 엔티티 객체]
     * @return [CustomUserDetail]
     */
    private CustomUserDetail createUserDetails(Member member) {
        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getAccountType().toString()));
        return new CustomUserDetail(
                member.getEmail(),
                member.getPassword(),
                authorities,
                member.getNickname(),
                member.getMemberId());

    }
}