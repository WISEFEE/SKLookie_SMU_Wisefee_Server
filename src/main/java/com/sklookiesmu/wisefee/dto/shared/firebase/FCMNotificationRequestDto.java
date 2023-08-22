package com.sklookiesmu.wisefee.dto.shared.firebase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private String to;
    private String priority;
    private Map<String, String> data;

    @Builder
    public FCMNotificationRequestDto(String to, String priority, Map<String, String> data) {
        this.to = to;
        this.priority = priority;
        this.data = data;
    }
}