package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장 수정 응답 DTO")
public class UpdateCafeResponseDto {

    @ApiModelProperty(value = "매장 ID", required = true)
    private Long cafeId;

    @ApiModelProperty(value = "매장명")
    private String title;

    @ApiModelProperty(value = "매장 설명")
    private String content;

    @ApiModelProperty(value = "매장 연락처")
    private String cafePhone;
}
