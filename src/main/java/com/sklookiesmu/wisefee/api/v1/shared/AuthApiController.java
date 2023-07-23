package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.common.auth.SecurityUtil;
import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.shared.jwt.TokenInfoDto;
import com.sklookiesmu.wisefee.dto.shared.member.LoginRequestDto;
import com.sklookiesmu.wisefee.service.shared.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    @ApiOperation(value = "기본 로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenInfoDto> login(@RequestBody LoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfoDto tokenInfo = authService.login(memberId, password);
        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }

    @ApiOperation(value = "토큰 재발급")
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @PostMapping("/refresh")
    public ResponseEntity<TokenInfoDto> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TokenInfoDto tokenInfo = authService.refresh(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }

    @ApiOperation(value = "샘플 API - 현재 로그인한 Member의 PK와 이메일 가져오기")
    @PreAuthorize(AuthConstant.AUTH_ROLE_COMMON_USER)
    @GetMapping("/user")
    public ResponseEntity<String> authSample(
    ){
        Long pk = SecurityUtil.getCurrentMemberPk();
        String email = SecurityUtil.getCurrentMemberEmail();
        String result = String.format("%d-%s", pk, email);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}