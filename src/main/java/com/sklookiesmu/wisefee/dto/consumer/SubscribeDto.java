package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SubscribeDto {

    /**
     * TODO : 구독권 만료일(OK), 구독권 사용횟수(OK), 구독권 정기 결제일, 구독 가입 여부(OK)
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

        @ApiModelProperty(value = "구독 가입여부", required = true)
        private String subStatus;

        @ApiModelProperty(value = "구독권 사용횟수", required = true)
        private int subCnt;

        @ApiModelProperty(value = "구독 인원", required = true)
        private Integer subPeople;

        @ApiModelProperty(value = "구독 발행일", required = true)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "구독 수정일", required = true)
        private LocalDateTime updatedAt;

        @ApiModelProperty(value = "구독 만료일", required = true)
        private LocalDateTime expiredAt;

        @ApiModelProperty(value = "구독권", required = true)
        private SubTicketTypeDto.SubTicketTypeResponseDto subTicketDto;

        @ApiModelProperty(value = "결제", required = true)
        private PaymentDto.PaymentResponseDto paymentDto;

        public static SubscribeResponseDto from(Subscribe subscribe) {
            return new SubscribeResponseDto(
                    subscribe.getSubId(),
                    subscribe.getTotalPrice(),
                    subscribe.getSubComment(),
                    subscribe.getSubStatus(),
                    subscribe.getSubCnt(),
                    subscribe.getSubPeople(),
                    subscribe.getCreatedAt(),
                    subscribe.getUpdatedAt(),
                    subscribe.getExpiredAt(),
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

        @ApiModelProperty(value = "구독 인원", required = true, example = "8")
        @NotNull(message = "구독 인원은 필수 입력값입니다.")
        private Integer subPeople;

        @ApiModelProperty(value = "구독 요청사항", required = true, example = "구독 요청사항입니다")
        private String subComment;

        @ApiModelProperty(value = "결제수단", required = true, example = "현장결제")
        private String paymentMethod;

        public Subscribe toEntity(Cafe cafe, SubTicketType subTicketType, Payment payment, Member member){
            return Subscribe.builder()
                    .subPeople(subPeople)
                    .subComment(subComment)
                    .subStatus("N")
                    .subCnt(0)
                    .subCntDay(0)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMonths(1))
                    .cafe(cafe)
                    .subTicketType(subTicketType)
                    .payment(payment)
                    .member(member)
                    .build();
        }
    }
}
