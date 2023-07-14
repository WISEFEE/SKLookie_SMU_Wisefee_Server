package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 수정 요청 DTO")
public class MemberUpdateRequestDto extends MemberRequestDto {
    @ApiModelProperty(value = "회원 PK", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "회원 닉네임", required = false)
    @Null
    private String name;

    @ApiModelProperty(value = "회원 비밀번호", required = false)
    @Null
    private String password;

    @ApiModelProperty(value = "회원 이메일", required = false)
    @Null
    private String email;

}
