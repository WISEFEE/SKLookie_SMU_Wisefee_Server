package com.sklookiesmu.wisefee.dto.shared.address;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 추가 요청 DTO")
public class AddressRequestDto {

    @NotBlank(message = "주소는 필수 입력값입니다.")
    @ApiModelProperty(value = "주소명", required = true, example = "전북 익산시 부송동 100")
    private String addressName;

    @NotBlank(message = "지역명1은 필수 입력값입니다.")
    @ApiModelProperty(value = "지역명1", required = true, example = "전북")
    private String region1DepthName;

    @NotBlank(message = "지역명2은 필수 입력값입니다.")
    @ApiModelProperty(value = "지역명2", required = true, example = "익산시")
    private String region2DepthName;

    @NotBlank(message = "지역명3은 필수 입력값입니다.")
    @ApiModelProperty(value = "지역명3", required = true, example = "부송동")
    private String region3DepthName;

    @NotBlank(message = "세부주소 정보는 필수 입력값입니다.")
    @ApiModelProperty(value = "세부주소", required = true, example = "루키아파트 102동 1000호")
    private String addressDetail;

    @NotNull(message = "X좌표(경도)는 필수 입력값입니다.")
    @ApiModelProperty(value = "X좌표(경도)", required = true, example = "126.99597295767953")
    private Double x;

    @NotNull(message = "Y좌표(위도)는 필수 입력값입니다.")
    @ApiModelProperty(value = "Y좌표(위도)", required = true, example = "35.97664845766847")
    private Double y;



}
