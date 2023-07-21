package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 수정 요청 DTO")
public class MemberUpdateRequestDto extends MemberRequestDto {


    @ApiModelProperty(value = "회원 닉네임", required = true)
    @NotNull
    private String nickname;

    @ApiModelProperty(value = "회원 이메일", required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "회원 연락처", required = true)
    @NotNull
    private String phone;

    @ApiModelProperty(value = "회원 사무용 연락처")
    private String phoneOffice;

    @ApiModelProperty(value = "회원 생년월일", required = true)
    @NotNull
    private String birth;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(value = "회원 계정 타입", required = true)
    @NotNull
    private String accountType;

    @ApiModelProperty(value = "회원 인증 타입", required = true)
    @NotNull
    private String authType;

    @ApiModelProperty(value = "이메일 인증 여부", required = true)
    @NotNull
    private String isAuthEmail;

    @ApiModelProperty(value = "알림 수신 여부", required = true)
    @NotNull
    private String isAllowPushMsg;

    @ApiModelProperty(value = "푸시 알림 토큰")
    private String pushMsgToken;



}
