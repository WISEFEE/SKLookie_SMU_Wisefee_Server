package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.common.auth.JwtTokenProvider;
import com.sklookiesmu.wisefee.domain.FbToken;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.repository.redis.AuthRepositoryWithRedis;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.apache.catalina.util.ConcurrentDateFormat.GMT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepositoryWithRedis authRepositoryWithRedis;

    @Transactional
    public TokenInfoDto login(String email, String password, String firebaseToken) {
        // 1. Login Email/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(authentication);

        Claims claims = jwtTokenProvider.parseClaims(tokenInfo.getAccessToken());                // exp 값을 가져오기
        Long exp = (Long) claims.getExpiration().getTime(); // 반환값은 밀리초 단위의 타임스탬프

        // TODO : R0822_Insert Redis
        Date date = new java.util.Date(exp);
        LocalDateTime expLocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        FbToken fbToken = FbToken.builder()
                .jwtToken(tokenInfo.getAccessToken())
                .memberPK(claims.get("userId", Long.class))
                .fireBaseToken(firebaseToken)
                .expire_date(expLocalDateTime)
                .build();

        FbToken save = authRepositoryWithRedis.save(fbToken);


        return tokenInfo;

    }

    @Transactional
    public TokenInfoDto refresh(Authentication auth, String jwt) {
        TokenInfoDto tokenInfo = jwtTokenProvider.generateToken(auth);

        // TODO : R0822_Update Redis, 기존 Redis의 jwt 토큰을 검색하여, 새로운 jwt로 대체
        Optional<FbToken> authInfo = authRepositoryWithRedis.findById(jwt);
        if (authInfo.isPresent()) {
            FbToken updateFbToken = FbToken.builder()
                    .jwtToken(tokenInfo.getAccessToken())
                    .memberPK(authInfo.get().getMemberPK())
                    .fireBaseToken(authInfo.get().getFireBaseToken())
                    .expire_date(authInfo.get().getExpire_date())
                    .build();

            FbToken save = authRepositoryWithRedis.save(updateFbToken);
            authRepositoryWithRedis.deleteById(jwt);
        } else {
            throw new RuntimeException("토큰이 존재하지 않습니다. 토큰을 새로 발급받으시기 바랍니다.");
        }


        return tokenInfo;
    }
}
