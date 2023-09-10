package com.sklookiesmu.wisefee.service.shared.interfaces;

import com.sklookiesmu.wisefee.common.enums.member.AuthType;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.dto.shared.member.OAuthGoogleTokenVerifyResponseDto;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface AuthService {

    /**
     * [디폴트 회원 로그인]
     * Email과 Password 기반 로그인
     * @param [email 이메일]
     * @param [password 비밀번호]
     * @param [firebaseToken FCM Token]
     * @param [loginAuthType 로그인 타입]
     * @return [TokenInfo]
     */
    public abstract TokenInfoDto login(String email, String password, String firebaseToken, AuthType loginAuthType);


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


    /**
     * [구글 Access Token을 통해 이메일을 얻어옴]
     * Google Access Token 검증, 검증 성공 시 해당 토큰의 Email을 얻어옴
     * @param [accessToken Google 계정 OAuth Access Token]
     * @return [String 이메일 주소]
     */
    public String getEmailByGoogleToken(String accessToken) throws IOException;

    /**
     * [구글 Access Token 검증]
     * Google Access Token 검증, 검증 성공 시 토큰에 해당하는 유저의 회원가입 상태를 나타내는 코드 반환
     * @param [accessToken Google 계정 OAuth Access Token]
     * @return [OAuthGoogleTokenVerifyResponseDto 회원가입 상태]
     */
    public OAuthGoogleTokenVerifyResponseDto verifyGoogleToken(String accessToken) throws IOException;

}
