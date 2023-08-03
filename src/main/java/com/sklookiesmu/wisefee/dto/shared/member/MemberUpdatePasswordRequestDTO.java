package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원 비밀번호 수정 DTO")
public class MemberUpdatePasswordRequestDTO {
    @ApiModelProperty(value = "회원 비밀번호", required = true, example = "Qqwert123@")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

}
