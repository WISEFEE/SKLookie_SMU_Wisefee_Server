package com.sklookiesmu.wisefee.common.auth;

import com.sklookiesmu.wisefee.common.auth.custom.CustomUserDetail;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.common.enums.member.AuthType;
import com.sklookiesmu.wisefee.common.exception.global.AuthForbiddenException;
import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.repository.redis.AuthRepositoryWithRedis;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * [JWT Token 관련 메서드를 제공하는 클래스]
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final AuthRepositoryWithRedis authRepositoryWithRedis;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, AuthRepositoryWithRedis authRepositoryWithRedis) {
        this.authRepositoryWithRedis = authRepositoryWithRedis;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * [JWT 토큰 생성]
     * 유저 정보를 통해 AccessToken을 생성
     * @param [Authentication 인증 정보 객체]
     * @return [TokenInfo]
     */
    public TokenInfoDto generateToken(Authentication authentication) {

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetails.getMemberId();
        String nickname = userDetails.getNickname();
        AuthType authType = userDetails.getAuthType();

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + AuthConstant.ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("userId", userId)
                .claim("nickname", nickname)
                .claim("auth", authorities)
                .claim("authType", authType)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + 86400000))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();




        return TokenInfoDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .build();
    }


    /**
     * [JWT 토큰 복호화]
     * JWT 토큰을 복호화하여 토큰에 들어있는 정보를 반환
     * @param [String accessToken]
     * @return [Authentication]
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new AuthForbiddenException("권한 정보가 없는 토큰입니다.");
        }
        System.out.println(accessToken);
        // TODO : R0822_Get Redis, Redis에 유저정보가 없으면 Error 반환
        Optional<FCMToken> authInfo = authRepositoryWithRedis.findById(accessToken);
        if (authInfo.isEmpty()) {
            throw new AuthForbiddenException("서버에 인증 정보가 존재하지 않습니다.");
        }


        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        CustomUserDetail userDetails = new CustomUserDetail(
                claims.getSubject(),
                "",
                authorities,
                (String)claims.get("nickname"),
                Long.valueOf((Integer)claims.get("userId")),
                AuthType.valueOf((String) claims.get("authType")));
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * [JWT 토큰 검증]
     * JWT 토큰을 검증하는 메서드
     * @param [String token]
     * @return [Boolean : Validate 여부]
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * [JWT 클래임 추출]
     * JWT 토큰 안의 Claim 정보를 추출
     * @param [String accessToken]
     * @return [Claims]
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * [요청에서 토큰 추출]
     * Request Header로부터 JWT 토큰을 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }



}