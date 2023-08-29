package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface AuthService {

    /**
     * [디폴트 회원 로그인]
     * Email과 Password 기반 로그인
     * @param [email 이메일]
     * @param [password 비밀번호]
     * @param [firebaseToken FCM Token]
     * @param [loginAccountType 로그인 타입]
     * @return [TokenInfo]
     */
    public abstract TokenInfoDto login(String email, String password, String firebaseToken, String loginAccountType);


    /**
     * [토큰 재발급]
     * 기존 토큰을 새로운 토큰으로 교체
     * @param [auth 인증 정보]
     * @param [jwt JWT]
     * @return [TokenInfo]
     */
    public abstract TokenInfoDto refresh(Authentication auth, String jwt);



    /**
     * [OAuth 구글 소셜 로그인]
     * Google Access Token을 통한 로그인
     * @param [accessToken Google 계정 OAuth Access Token]
     * @param [firebaseToken FCM Token]
     * @return [TokenInfo]
     */
    public TokenInfoDto googleLogin(String accessToken, String firebaseToken) throws IOException;

}
