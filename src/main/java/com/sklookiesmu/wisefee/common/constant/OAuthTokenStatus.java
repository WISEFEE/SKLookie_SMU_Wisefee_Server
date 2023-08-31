package com.sklookiesmu.wisefee.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthTokenStatus {

    ALREADY(0, "이미 소셜 가입된 계정입니다. 로그인을 진행해주세요"),
    VOID(1, "소셜 가입되지 않은 계정입니다. 소셜 회원가입을 진행해주세요."),
    EXIST(2, "이미 일반 회원으로 가입된 계정입니다."),
    FAIL_AUTH(3,"토큰의 유효성 검증에 실패하였습니다. 구글 인증을 다시 진행해주세요.");


    private final Integer value;
    private final String message;
    @JsonValue
    public String getMessage(){
        return this.message;}
}
