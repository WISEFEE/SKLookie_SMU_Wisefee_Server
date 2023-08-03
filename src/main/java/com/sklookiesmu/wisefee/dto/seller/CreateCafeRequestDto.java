package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장 추가 요청 DTO")
public class CreateCafeRequestDto {

    @ApiModelProperty(value = "매장명", required = true)
    @NotBlank(message = "매장명은 필수 입력 값입니다.")
    private String title;

    @ApiModelProperty(value = "매장 설명")
    private String content;

    @ApiModelProperty(value = "매장 연락처", required = true)
    @NotBlank(message = "매장장 연락처는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9자리 이상 11자리 이하의 숫자만 가능합니다.")
    private String cafePhone;

    @ApiModelProperty(value = "매장 주소 ID", required = true)
    @NotNull
    private Long addrId;

}
