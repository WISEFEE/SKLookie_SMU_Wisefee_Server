package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.auth.JwtTokenProvider;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfoDto login(String email, String password, String firebaseToken) {
        // 1. Login Email/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(authentication);

        // TODO : R0822_Insert Redis



        return tokenInfo;

    }

    @Transactional
    public TokenInfoDto refresh(Authentication auth, String jwt) {
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(auth);

        // TODO : R0822_Update Redis, 기존 Redis의 jwt 토큰을 검색하여, 새로운 jwt로 대체


        return tokenInfo;
    }

}
