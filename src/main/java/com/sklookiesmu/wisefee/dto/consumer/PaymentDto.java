package com.sklookiesmu.wisefee.dto.consumer;

import com.sklookiesmu.wisefee.domain.Payment;
import com.sklookiesmu.wisefee.domain.SubTicketType;
import com.sklookiesmu.wisefee.domain.Subscribe;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PaymentDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResponseDto{

        @ApiModelProperty(value = "결제 총금액")
        private long paymentPrice;

        /*@ApiModelProperty(value = "보증금")
        private int depositPrice;*/

        @ApiModelProperty(value = "결제 내역 정보")
        private String paymentInfo;

        @ApiModelProperty(value = "결제 수단 정보")
        private String paymentMethod;

        @ApiModelProperty(value = "결제 생성일")
        private LocalDateTime createdAt;

        public static PaymentResponseDto from(Payment payment){
            return new PaymentResponseDto(
                    payment.getPaymentPrice(),
                    payment.getPaymentInfo(),
                    payment.getPaymentMethod(),
                    payment.getCreatedAt());
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRequestDto{

        @ApiModelProperty(value = "결제 수단", example = "현장결제")
        @NotNull(message = "결제 수단은 필수 입력값입니다.")
        private String paymentMethod;

       /* public Payment toEntity(Payment payment){
            return Payment.builder()
                    .paymentInfo(payment.getPaymentInfo())
                    .paymentPrice(payment.getPaymentPrice())
                    .paymentMethod(paymentMethod)
                    .build();
        }*/
    }
}
