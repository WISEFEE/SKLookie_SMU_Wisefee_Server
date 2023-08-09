package com.sklookiesmu.wisefee.dto.shared.subTicket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "구독권 유형 추가 요청 DTO")
public class SubTicketTypeRequestDto {

    @NotBlank(message = "구독권 이름은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 이름", required = true, example = "소규모 구독권")
    private String subTicketName;

    @NotNull(message = "구독권 가격은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 가격", required = true, example = "12900")
    private int subTicketPrice;

    @NotNull(message = "구독권 최소 인원조건은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 최소 인원조건", required = true, example = "6")
    private int subTicketMinUserCount;

    @NotNull(message = "구독권 최대 인원조건은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 최대 인원조건", required = true, example = "10")
    private int subTicketMaxUserCount;

    @NotNull(message = "인당 텀블러 보증금 가격은 필수 입력값입니다.")
    @ApiModelProperty(value = "인원당 텀블러 보증금 가격", required = true, example = "1000")
    private int subTicketDeposit;

    @NotNull(message = "기본 할인률 입력은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 기본 할인률", required = true, example = "5")
    private int subTicketBaseDiscountRate;

    @NotNull(message = "인원당 추가 할인률 입력은 필수 입력값입니다.")
    @ApiModelProperty(value = "인원당 추가 할인률", required = true, example = "1")
    private int subTicketAdditionalDiscountRate;

    @NotNull(message = "총 최대 할인률은 필수 입력값입니다.")
    @ApiModelProperty(value = "총 최대 할인률", required = true, example = "10")
    private int subTicketMaxDiscountRate;

    @NotNull(message = "구독권 설명은 필수 입력값입니다.")
    @ApiModelProperty(value = "구독권 설명", required = true, example = "주기적 와이즈피 이용 단체 대상")
    private String subTicketDescription;



}
