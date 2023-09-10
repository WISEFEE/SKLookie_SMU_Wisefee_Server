package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.JwtTokenProvider;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.dto.shared.member.LoginOAuthGoogleRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.LoginRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.OAuthGoogleTokenVerifyRequestDto;
import com.sklookiesmu.wisefee.dto.shared.member.OAuthGoogleTokenVerifyResponseDto;
import com.sklookiesmu.wisefee.service.shared.interfaces.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "COMM-B :: 인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(
            value = "COMM-B-01 :: 기본 로그인",
            notes = "기본 로그인입니다. 회원가입되어 있는 사용자의 ID와 비밀번호가 필요합니다. <br>" +
                    "반환되는 JWT 토큰은 다른 API들을 사용할 때 " +
                    "Request Header에 Authorization Bearer JWT를 추가하여 보내주셔야 인가가 승인되어 API를 사용할 수 있습니다. <br><br>" +
                    "JWT 토큰은 회원가입 시 선택된 회원유형(고객/매장)에 따라 다른 종류의 토큰이 발급됩니다. " +
                    "consumer API는 고객, seller API는 매장 로그인 과정에서 나온 토큰을 사용해야 인가가 이루어집니다. <br><br>" +
                    "Token의 Payload에는 {sub(이메일), userId(Member PK), nickname(닉네임), " +
                    "auth(ROLE_CONSUMER/ROLE_SELLER), exp(만료시간), authType(로그인 유형)}이 포함되어 있고, 프론트엔드 측에서도 JWT 라이브러리 등을 통해 " +
                    "복호화하여 내용을 볼 수 있으니, 필요 시 사용하시면 됩니다.<br><br>" +
                    "0825 : 푸시 알림을 위한 FCMToken을 선택적 Input으로 받습니다. 기기에서 푸시알림 허용이 되어있다면 해당 값을 넣어서 보내주시면 됩니다."
    )
    @PostMapping("/login")
    public ResponseEntity<TokenInfoDto> login(@RequestBody LoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfoDto tokenInfo = authService.login(memberId, password, memberLoginRequestDto.getFcmToken(), AuthConstant.AUTH_TYPE_COMMON);
        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }

    @ApiOperation(
            value = "COMM-B-02 :: 토큰 재발급",
            notes = "토큰 재발급 API입니다. 프론트엔드 측에서는 토큰이 만료되는 시간이 되기 전에(payload의 exp를 체크) 해당 API를 호출해서" +
                    "토큰을 재발급받는 로직을 넣어야, 재로그인 필요 없이 API 사용이 가능합니다."
    )
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping("/refresh")
    public ResponseEntity<TokenInfoDto> refreshToken(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        TokenInfoDto tokenInfo = authService.refresh(authentication, token);

        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }



    @ApiOperation(
            value = "COMM-B-G1 :: OAuth Google : 구글 소셜 로그인",
            notes = "토큰 재발급 API입니다. 프론트엔드 측에서는 토큰이 만료되는 시간이 되기 전에(payload의 exp를 체크) 해당 API를 호출해서<br>" +
                    "토큰을 재발급받는 로직을 넣어야, 재로그인 필요 없이 API 사용이 가능합니다."
    )
    @PostMapping("/google/login")
    public ResponseEntity<TokenInfoDto> googleLogin(@RequestBody LoginOAuthGoogleRequestDto loginRequest) throws IOException {
        TokenInfoDto tokenInfo = authService.googleLogin(loginRequest.getGoogleAccessToken(), loginRequest.getFcmToken());
        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }



    @ApiOperation(
            value = "COMM-B-G2 :: OAuth Google : Google Access Token을 통해 가입/로그인 필요여부 판별",
            notes = "OAuth 인증 후 발급받은 Google Access Token을 판별합니다<br>" +
                    "해당 Access Token이 회원가입이 필요한 지, 이미 회원가입이 되어 있으니 로그인을 진행하면 되는지, 이미 일반 계정(Common)으로 가입되었는지를 판별합니다.<br>" +
                    "따라서 해당 API 호출 후, COMM-B-G1(로그인) 혹은 COMM-C-G1(회원가입) API를 실행시키면 됩니다. <br>" +
                    "응답의 자세한 판별코드 종류와 내용은 해당 API Swagger Response의 Schema를 반드시 확인"

    )
    @PostMapping("/google/verify")
    public ResponseEntity<OAuthGoogleTokenVerifyResponseDto> varifyAccessToken(
            @RequestBody
            OAuthGoogleTokenVerifyRequestDto request) throws IOException {
        OAuthGoogleTokenVerifyResponseDto res = authService.verifyGoogleToken(request.getGoogleAccessToken());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}