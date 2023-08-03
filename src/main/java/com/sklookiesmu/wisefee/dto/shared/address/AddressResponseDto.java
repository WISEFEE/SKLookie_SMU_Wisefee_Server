package com.sklookiesmu.wisefee.dto.shared.address;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "주소 응답 Dto")
public class AddressResponseDto {
    @ApiModelProperty(value = "주소 PK", required = true)
    @NotNull
    private Long addrId;

    @ApiModelProperty(value = "주소명", required = true)
    private String addressName;

    @ApiModelProperty(value = "지역명1", required = true)
    private String region1DepthName;

    @ApiModelProperty(value = "지역명2", required = true)
    private String region2DepthName;

    @ApiModelProperty(value = "지역명3", required = true)
    private String region3DepthName;

    @ApiModelProperty(value = "상세 주소", required = true)
    private String addressDetail;

    @ApiModelProperty(value = "X좌표(경도)", required = true)
    private Double x;

    @ApiModelProperty(value = "Y좌표(위도)", required = true)
    private Double y;

    @ApiModelProperty(value = "생성일")
    private String createdAt;

    @ApiModelProperty(value = "수정일")
    private String updatedAt;
}