package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 이메일 기반 정보 조회 DTO")
public class MemberEmailResponseDto {
    @ApiModelProperty(value = "회원 닉네임", required = true)
    private String nickname;

    @ApiModelProperty(value = "회원 연락처", required = true)
    private String phone;

    @ApiModelProperty(value = "회원 사무용 연락처")
    private String phoneOffice;

    @ApiModelProperty(value = "회원 생년월일", required = true)
    private String birth;

    @ApiModelProperty(value = "회원 계정 타입", required = true)
    private String accountType;

    @ApiModelProperty(value = "회원 인증 타입", required = true)
    private String authType;

    @ApiModelProperty(value = "이메일 인증 여부", required = true)
    private String isAuthEmail;

    @ApiModelProperty(value = "알림 수신 여부", required = true)
    private String isAllowPushMsg;
}
