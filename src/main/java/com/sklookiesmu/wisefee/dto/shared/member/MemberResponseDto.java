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
@ApiModel(description = "회원 추가 요청 DTO")
public class MemberResponseDto {

    @ApiModelProperty(value = "회원 아이디")
    private String memberId;

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

    @ApiModelProperty(value = "회원 상태")
    private String memberStatus;

    @ApiModelProperty(value = "생성일")
    private String createdAt;

    @ApiModelProperty(value = "수정일")
    private String updatedAt;
}
