package com.sklookiesmu.wisefee.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    REQUESTED(0, "요청"),
    ACCEPT(1, "접수"),
    REJECT(2, "거절"),
    ALLSET(3,"준비완료"),
    RECEIVE(4, "수령"),
    DONE(5, "완료 (텀블러 반납)");


    private final Integer value;
    private final String status;
    @JsonValue
    public String getStatus(){
        return this.status;}
}
