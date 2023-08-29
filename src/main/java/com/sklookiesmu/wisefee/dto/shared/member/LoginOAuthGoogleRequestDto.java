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
@ApiModel(description = "Google 로그인 요청 DTO")
public class LoginOAuthGoogleRequestDto {
    @ApiModelProperty(value = "Google OAuth Access Token", required = true)
    @NotNull
    private String googleAccessToken;

    @ApiModelProperty(value = "파이어베이스 FCM Token(선택)", required = false)
    private String fcmToken;

}
