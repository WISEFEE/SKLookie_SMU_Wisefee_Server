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

    @ApiModelProperty(value = "회원 PK", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "회원 닉네임", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "회원 이메일", required = true)
    @NotNull
    private String email;
}
