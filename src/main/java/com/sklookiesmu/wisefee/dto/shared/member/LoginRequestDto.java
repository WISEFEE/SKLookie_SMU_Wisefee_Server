package com.sklookiesmu.wisefee.dto.shared.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "로그인 요청 DTO")
public class LoginRequestDto {
    @ApiModelProperty(value = "회원 이메일", required = true, example = "testtest@gmail.com")
    @NotNull
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", required = true, example = "test123!")
    @NotNull
    private String password;


    @ApiModelProperty(value = "파이어베이스 FCM Token(푸시알림 동의여부에 따른 Optional Param, 해당 값을 넣어서 로그인해야 해당 기기로 푸시 알림이 전송되도록 함)", required = false, example = "YOUR_FCM_TOKEN or NULL")
    private String fcmToken;

}
