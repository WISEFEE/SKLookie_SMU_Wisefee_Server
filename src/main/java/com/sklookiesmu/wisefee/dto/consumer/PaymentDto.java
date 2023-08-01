package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Payment;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.domain.Subscribe;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

public class PaymentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResponseDto{

        @ApiModelProperty(value = "결제 PK")
        private Long paymentId;

        @ApiModelProperty(value = "결제 금액")
        private Integer paymentPrice;

        @ApiModelProperty(value = "결제 내역 정보")
        private String paymentInfo;

        @ApiModelProperty(value = "결제 생성일")
        private LocalDateTime createdAt;

        public static PaymentResponseDto from(Payment payment){
            return new PaymentResponseDto(
                    payment.getPaymentId(),
                    payment.getPaymentPrice(),
                    payment.getPaymentInfo(),
                    payment.getCreatedAt());
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRequestDto{

        @ApiModelProperty(value = "결제 금액")
        private Integer paymentPrice;

        @ApiModelProperty(value = "결제 내역 정보")
        private String paymentInfo;

        public Payment toEntity(SubTicketType subTicketType){
            return Payment.builder()
                    .paymentInfo(paymentInfo)
                    .paymentPrice(subTicketType.getSubTicketPrice())
                    .build();
        }
    }
}
