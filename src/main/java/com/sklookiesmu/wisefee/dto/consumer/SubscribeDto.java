package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SubscribeDto {

    /**
     * TODO : 구독권 만료일, 구독권 사용횟수, 구독권 정기 결제일, 구독 가입 여부
     */
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SubscribeResponseDto{

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
        private SubTicketTypeDto.SubTicketTypeResponseDto subTicketDto;

        @ApiModelProperty(value = "결제", required = true)
        private PaymentDto.PaymentResponseDto paymentDto;

        public static SubscribeResponseDto from(Subscribe subscribe) {
            return new SubscribeResponseDto(
                    subscribe.getSubId(),
                    subscribe.getTotalPrice(),
                    subscribe.getSubComment(),
                    subscribe.getSubType(),
                    subscribe.getSubPeople(),
                    subscribe.getCreatedAt(),
                    subscribe.getUpdatedAt(),
                    SubTicketTypeDto.SubTicketTypeResponseDto.from(subscribe.getSubTicketType()), // DTO로 구현한 이유 : LAZY 로딩으로 인한 Json 반환 오류
                    PaymentDto.PaymentResponseDto.from(subscribe.getPayment()));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class SubscribeListResponseDto{
        private List<SubscribeResponseDto> subscribes;

        public static SubscribeListResponseDto of(List<Subscribe> subscribeList) {
            List<SubscribeResponseDto> list = subscribeList.stream()
                    .map(SubscribeResponseDto::from)
                    .collect(Collectors.toList());

            return new SubscribeListResponseDto(list);
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


        public Subscribe toEntity(Cafe cafe, SubTicketType subTicketType, Payment payment, Member member){
            return Subscribe.builder()
                    .subType(subType)
                    .subPeople(subPeople)
                    .subComment(subComment)
                    .cafe(cafe)
                    .subTicketType(subTicketType)
                    .payment(payment)
                    .member(member)
                    .build();
        }
    }
}
