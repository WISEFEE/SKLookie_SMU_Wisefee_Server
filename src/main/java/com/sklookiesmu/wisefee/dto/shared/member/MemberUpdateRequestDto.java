package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 수정 요청 DTO")
public class MemberUpdateRequestDto {

    @ApiModelProperty(value = "회원 닉네임", required = true, example = "TomAngel")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @ApiModelProperty(value = "회원 연락처", required = true, example = "01012345678")
    @NotBlank(message = "회원 연락처는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.")
    private String phone;

    @ApiModelProperty(value = "회원 사무용 연락처", example = "0299999999")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.")
    private String phoneOffice;

    @ApiModelProperty(value = "회원 생년월일", required = true, example = "19980101")
    @NotBlank(message = "회원 생년월일은 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "생년월일은 yyyyMMdd 형식으로 입력해야 합니다.")
    private String birth;

    @ApiModelProperty(value = "이메일 인증 여부", required = true, example = "FALSE")
    @NotBlank(message = "이메일 인증 여부는 필수 입력 값입니다.")
    @Pattern(regexp = "^(TRUE|FALSE)", message = "이메일 인증 여부는 TURE, FALSE 중 하나여야 합니다.")
    private String isAuthEmail;

    @ApiModelProperty(value = "알림 수신 여부", required = true, example = "TRUE")
    @NotBlank(message = "알림 수신 여부는 필수 입력 값입니다.")
    @Pattern(regexp = "^(TRUE|FALSE)", message = "알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.")
    private String isAllowPushMsg;


}
