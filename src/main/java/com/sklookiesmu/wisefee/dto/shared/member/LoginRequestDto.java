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
@ApiModel(description = "로그인 요청 DTO")
public class LoginRequestDto {
    @ApiModelProperty(value = "회원 이메일", required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    @NotNull
    private String password;

}
