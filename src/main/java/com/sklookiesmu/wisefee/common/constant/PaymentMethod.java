package com.sklookiesmu.wisefee.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {

    CASH(0, "현금"),
    CARD(1, "카드"),
    ETC(2, "기타");

    private final Integer value;
    private final String method;

    @JsonValue
    public String getPay() {
        return this.method;
    }
}
