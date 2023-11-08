package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원가입 DTO")
public class MemberRequestDto {
    @ApiModelProperty(value = "회원 닉네임", required = true, example = "Tom")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @ApiModelProperty(value = "회원 이메일", required = true, example = "testtest@gmail.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @ApiModelProperty(value = "회원 연락처", required = true, example = "01012345678")
    @NotBlank(message = "회원 연락처는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.")
    private String phone;

    @ApiModelProperty(value = "회원 사무용 연락처", example = "021111234")
    @NotBlank(message = "회원 사무용 연락처는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.")
    private String phoneOffice;

    @ApiModelProperty(value = "회원 생년월일", required = true, example = "19980101")
    @NotBlank(message = "회원 생년월일은 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "생년월일은 yyyyMMdd 형식으로 입력해야 합니다.")
    private String birth;

    @ApiModelProperty(value = "비밀번호", required = true, example = "test123!")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @ApiModelProperty(value = "회원 계정 타입", required = true, example = "CONSUMER")
    @NotBlank(message = "계정 타입은 필수 입력 값입니다.")
    @Pattern(regexp = "^(CONSUMER|SELLER)$", message = "계정 타입은 CONSUMER, SELLER 중 하나여야 합니다.")
    private String accountType;


    @ApiModelProperty(value = "이메일 인증 여부", required = true, example = "TRUE")
    @NotBlank(message = "이메일 인증 여부는 필수 입력 값입니다.")
    @Pattern(regexp = "^(TRUE|FALSE)", message = "이메일 인증 여부는 TURE, FALSE 중 하나여야 합니다.")
    private String isAuthEmail;

    @ApiModelProperty(value = "알림 수신 여부", required = true, example = "FALSE")
    @NotBlank(message = "알림 수신 여부는 필수 입력 값입니다.")
    @Pattern(regexp = "^(TRUE|FALSE)", message = "알림 수신 여부는 TRUE, FALSE 중 하나여야 합니다.")
    private String isAllowPushMsg;

}
