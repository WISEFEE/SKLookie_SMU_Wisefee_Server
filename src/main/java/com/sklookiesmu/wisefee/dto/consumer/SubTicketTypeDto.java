package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.SubTicketType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubTicketTypeDto {

    @Getter
    //@NoArgsConstructor
    @AllArgsConstructor
    public static class SubTicketTypeResponseDto{

        @ApiModelProperty(value = "정기구독권 종류 PK")
        private Long subTicketId;

        @ApiModelProperty(value = "정기구독권 이름")
        private String subTicketName;

       @ApiModelProperty(value = "정기구독권 가격")
        private int subTicketPrice;

        public static SubTicketTypeResponseDto from(SubTicketType subTicketType) {
            log.info("subTicketTypeId : " + subTicketType.getSubTicketId());

            return new SubTicketTypeResponseDto(
                    subTicketType.getSubTicketId(),
                    subTicketType.getSubTicketName(),
                    subTicketType.getSubTicketPrice());
        }
    }
}
