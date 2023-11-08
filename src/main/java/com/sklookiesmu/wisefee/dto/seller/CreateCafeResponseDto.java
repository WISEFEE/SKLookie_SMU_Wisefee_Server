package com.sklookiesmu.wisefee.dto.seller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "매장 추가 응답 DTO")
public class CreateCafeResponseDto {

    @ApiModelProperty(value = "매장 ID", required = true)
    private Long id;
}
