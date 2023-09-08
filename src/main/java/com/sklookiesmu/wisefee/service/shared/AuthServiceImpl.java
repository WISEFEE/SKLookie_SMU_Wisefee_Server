package com.sklookiesmu.wisefee.service.shared;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sklookiesmu.wisefee.common.auth.JwtTokenProvider;
import com.sklookiesmu.wisefee.common.auth.custom.CustomUserDetail;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.constant.OAuthTokenStatus;
import com.sklookiesmu.wisefee.common.exception.AuthForbiddenException;
import com.sklookiesmu.wisefee.domain.Member;
import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.dto.shared.member.OAuthGoogleTokenVerifyResponseDto;
import com.sklookiesmu.wisefee.repository.MemberRepository;
import com.sklookiesmu.wisefee.repository.redis.AuthRepositoryWithRedis;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepositoryWithRedis authRepositoryWithRedis;

    private final MemberRepository memberRepository;


    /**
     * [디폴트 회원 로그인]
     * Email과 Password 기반 로그인
     * @param [email 이메일]
     * @param [password 비밀번호]
     * @param [firebaseToken FCM Token]
     * @param [loginAccountType 로그인 타입]
     * @return [TokenInfo]
     */
    @Transactional
    public TokenInfoDto login(String email, String password, String firebaseToken, String loginAuthType) {
        // 1. Login Email/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        CustomUserDetail userDetail = (CustomUserDetail)authentication.getPrincipal();
        String authType = userDetail.getAuthType();

        if(!authType.equalsIgnoreCase(loginAuthType)){
            throw new AuthForbiddenException(loginAuthType + " 로그인으로 유효한 계정 타입이 아닙니다.");
        }

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(authentication);


        if(firebaseToken != null && !firebaseToken.isEmpty()){
            Optional<List<FCMToken>> fbTokens = authRepositoryWithRedis.findAllByfireBaseToken(firebaseToken);
            fbTokens.ifPresent(authRepositoryWithRedis::deleteAll);
        }



        //Insert Redis
        Claims claims = jwtTokenProvider.parseClaims(tokenInfo.getAccessToken());                // exp 값을 가져오기
        Long exp = (Long) claims.getExpiration().getTime(); // 반환값은 밀리초 단위의 타임스탬프
        Date date = new java.util.Date(exp);
        LocalDateTime expLocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        FCMToken token = FCMToken.builder()
                .jwtToken(tokenInfo.getAccessToken())
                .memberPK(claims.get("userId", Long.class))
                .fireBaseToken(firebaseToken)
                .ttl(Duration.between(LocalDateTime.now(), expLocalDateTime).getSeconds())
                .build();

        FCMToken save = authRepositoryWithRedis.save(token);


        return tokenInfo;

    }



    /**
     * [토큰 재발급]
     * 기존 토큰을 새로운 토큰으로 교체
     * @param [auth 인증 정보]
     * @param [jwt JWT]
     * @return [TokenInfo]
     */
    @Transactional
    public TokenInfoDto refresh(Authentication auth, String jwt) {
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(auth);

        //Update Redis, 기존 Redis의 jwt 토큰을 검색하여, 새로운 jwt로 대체
        Optional<FCMToken> authInfo = authRepositoryWithRedis.findById(jwt);
        Claims claims = jwtTokenProvider.parseClaims(tokenInfo.getAccessToken());                // exp 값을 가져오기
        Long exp = (Long) claims.getExpiration().getTime(); // 반환값은 밀리초 단위의 타임스탬프
        Date date = new java.util.Date(exp);
        LocalDateTime expLocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (authInfo.isPresent()) {
            FCMToken updateFCMToken = FCMToken.builder()
                    .jwtToken(tokenInfo.getAccessToken())
                    .memberPK(authInfo.get().getMemberPK())
                    .fireBaseToken(authInfo.get().getFireBaseToken())
                    .ttl(Duration.between(LocalDateTime.now(), expLocalDateTime).getSeconds())
                    .build();

            FCMToken save = authRepositoryWithRedis.save(updateFCMToken);
            authRepositoryWithRedis.deleteById(jwt);
        } else {
            throw new RuntimeException("토큰이 존재하지 않습니다. 토큰을 새로 발급받으시기 바랍니다.");
        }


        return tokenInfo;
    }


    /**
     * [OAuth 구글 소셜 로그인]
     * Google Access Token을 통한 로그인
     * @param [accessToken Google 계정 OAuth Access Token]
     * @param [firebaseToken FCM Token]
     * @return [TokenInfo]
     */
    @Transactional
    public TokenInfoDto googleLogin(String accessToken, String firebaseToken) throws IOException {

        String email = getEmailByGoogleToken(accessToken);
        String pwd = AuthConstant.OAUTH_PASSWORD;
        TokenInfoDto token = login(email, pwd, firebaseToken, AuthConstant.AUTH_TYPE_GOOGLE);
        return token;

    }


    /**
     * [구글 Access Token 검증]
     * Google Access Token 검증, 검증 성공 시 해당 토큰의 Email을 얻어옴
     * @param [accessToken Google 계정 OAuth Access Token]
     * @return [String 이메일 주소]
     */
    public String getEmailByGoogleToken(String accessToken) throws IOException {
        String url = AuthConstant.OAUTH_GOOGLE_AUTHENTICATION_URL + "?access_token=" + accessToken;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request)
                .execute();

        if (response.code() == HttpStatus.SC_OK) {
            String jsonResponse = response.body().string();
            String email = extractEmail(jsonResponse);
            if(email == null){
                throw new RuntimeException("OAuth 서버에서 인증 정보를 가져오는데 문제 발생");
            }
            return email;
        } else {
            throw new AuthForbiddenException("사용자 토큰이 유효하지 않습니다.");
        }

    }


    /**
     * [Json으로부터 Email 추출]
     * @param [String JSON]
     * @return [String 이메일 주소]
     */
    public String extractEmail(String jsonResponse) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();

        if (jsonObject.has("email")) {
            return jsonObject.get("email").getAsString();
        } else {
            return null;
        }
    }



    /**
     * [구글 Access Token 검증]
     * Google Access Token 검증, 검증 성공 시 토큰에 해당하는 유저의 회원가입 상태를 나타내는 코드 반환
     * @param [accessToken Google 계정 OAuth Access Token]
     * @return [OAuthGoogleTokenVerifyResponseDto 회원가입 상태]
     */
    @Transactional
    public OAuthGoogleTokenVerifyResponseDto verifyGoogleToken(String accessToken) throws IOException{
        String url = AuthConstant.OAUTH_GOOGLE_AUTHENTICATION_URL + "?access_token=" + accessToken;
        OAuthGoogleTokenVerifyResponseDto result = new OAuthGoogleTokenVerifyResponseDto();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request)
                .execute();

        if (response.code() == HttpStatus.SC_OK) {
            String jsonResponse = response.body().string();
            String email = extractEmail(jsonResponse);
            if(email == null){
                throw new RuntimeException("OAuth 서버에서 인증 정보를 가져오는데 문제 발생");
            }
            else{
                Optional<Member> member = memberRepository.findByEmail(email);
                if(member.isPresent()){
                    if(member.get().getAuthType().equalsIgnoreCase(AuthConstant.AUTH_TYPE_GOOGLE)){
                        //CASE2 : 이미 구글로 가입된 토큰
                        result.setCode(OAuthTokenStatus.ALREADY.getValue());
                        result.setDesc(OAuthTokenStatus.ALREADY.getMessage());
                    }
                    else{
                        //CASE3 : 이미 일반 회원으로 가입된 토큰
                        result.setCode(OAuthTokenStatus.EXIST.getValue());
                        result.setDesc(OAuthTokenStatus.EXIST.getMessage());
                    }
                }
                else{
                    //CASE1 : 회원가입 필요 토큰
                    result.setCode(OAuthTokenStatus.VOID.getValue());
                    result.setDesc(OAuthTokenStatus.VOID.getMessage());
                }
            }

        } else {
            // CASE4 : 유효하지 않은 토큰
            result.setCode(OAuthTokenStatus.FAIL_AUTH.getValue());
            result.setDesc(OAuthTokenStatus.FAIL_AUTH.getMessage());
        }
        return result;
    }

}
