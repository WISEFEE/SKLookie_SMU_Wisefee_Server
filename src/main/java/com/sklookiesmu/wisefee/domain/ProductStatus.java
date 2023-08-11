package com.sklookiesmu.wisefee.domain;

public enum ProductStatus {
    REQUESTED,  // 주문 요청
    ACCEPTED,   // 주문 접수
    REJECTED,   // 주문 거절
    PREPARING,  // 준비 중
    COMPLETED,  // 준비 완료
    COLLECTED   // 수령 완료
}
