package com.sklookiesmu.wisefee.dto.shared.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "로그인 요청 DTO")
public class TokenInfoDto {

    @ApiModelProperty(value = "토큰 타입", required = true)
    @NotNull
    private String grantType;

    @ApiModelProperty(value = "Access Token", required = true)
    @NotNull
    private String accessToken;

}
