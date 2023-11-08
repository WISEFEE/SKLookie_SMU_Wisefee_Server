package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        description = "Google Access 토큰 판별 응답 <br>" +
                "code : 0 == 이미 소셜 가입된 계정. 로그인 진행하면 됨 <br>" +
                "code : 1 == 소셜 가입되지 않은 계정. 소셜 회원가입 진행하면 됨 <br>" +
                "code : 2 == 이미 일반회원으로 가입된 계정. 소셜 회원가입 및 로그인 이용 못함 (추후 일반회원->소셜회원 전환 로직 만들수도 있음) <br>" +
                "code : 3 == 토큰 검증 실패. 구글 인증 재시도 해보라고 안내 <br>"

)
public class OAuthGoogleTokenVerifyResponseDto {

    @ApiModelProperty(value = "판별 코드", required = true)
    @NotNull
    private int code;

    @ApiModelProperty(value = "판별 내용", required = true)
    @NotNull
    private String desc;


}
