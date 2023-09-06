package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Access Token 판별 요청 Dto")
public class OAuthGoogleTokenVerifyRequestDto {
    @ApiModelProperty(value = "Google OAuth Access Token", required = true, example = "YOUR_GOOGLE_OAUTH_ACCESS_TOKEN")
    @NotBlank(message = "구글 Access Token은 필수 입력 값입니다.")
    private String googleAccessToken;


}
