package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.domain.Subscribe;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

public class SubscribeDto {

    /**
     * TODO : 구독권 만료일, 구독권 사용횟수, 구독권 정기 결제일, 구독 가입 여부
     */
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubScribeResponseDto{

        @ApiModelProperty(value = "구독 PK", required = true)
        private Long subId;

        @ApiModelProperty(value = "구독 금액", required = true)
        private Integer totalPrice;

        @ApiModelProperty(value = "구독 요청사항", required = true)
        private String subComment;

        @ApiModelProperty(value = "구독권 유형", required = true)
        private String subType;

        @ApiModelProperty(value = "구독 인원", required = true)
        private Integer subPeople;

        @ApiModelProperty(value = "구독 발행일", required = true)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "구독 수정일", required = true)
        private LocalDateTime updatedAt;

        @ApiModelProperty(value = "구독권", required = true)
        private SubTicketType subTicketType;


        public static SubScribeResponseDto from(Subscribe subscribe){
            return new SubScribeResponseDto(
                    subscribe.getSubId(),
                    subscribe.getTotalPrice(),
                    subscribe.getSubComment(),
                    subscribe.getSubType(),
                    subscribe.getSubPeople(),
                    subscribe.getCreatedAt(),
                    subscribe.getUpdatedAt(),
                    subscribe.getSubTicketType());
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubscribeRequestDto{

        @ApiModelProperty(value = "구독권 유형", required = true)
        private String subType;

        @ApiModelProperty(value = "구독 인원", required = true)
        private Integer subPeople;

        @ApiModelProperty(value = "구독 요청사항", required = true)
        private String subComment;


        public Subscribe toEntity(Cafe cafe, SubTicketType subTicketType){
            return Subscribe.builder()
                    .subType(subType)
                    .subPeople(subPeople)
                    .subComment(subComment)
                    .cafe(cafe)
                    .subTicketType(subTicketType)
                    .build();
        }
    }
}
