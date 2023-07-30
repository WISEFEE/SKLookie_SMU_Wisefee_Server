package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장 추가 요청 DTO")
public class CreateCafeRequestDto {

    @ApiModelProperty(value = "매장명", required = true)
    @NotNull
    private String title;

    @ApiModelProperty(value = "매장 설명")
    private String content;

    @ApiModelProperty(value = "매장 연락처", required = true)
    @NotNull
    private String cafePhone;

    @ApiModelProperty(value = "매장 주소 ID", required = true)
    @NotNull
    private Long addrId;

}
