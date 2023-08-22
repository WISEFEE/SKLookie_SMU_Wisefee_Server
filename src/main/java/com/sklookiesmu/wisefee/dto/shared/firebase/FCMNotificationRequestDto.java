package com.sklookiesmu.wisefee.dto.shared.firebase;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private String targetUserToken;
    private String title;
    private String body;
    // private String image;
    // private Map<String, String> data;

    @Builder
    public FCMNotificationRequestDto(String targetUserToken, String title, String body) {
        this.targetUserToken = targetUserToken;
        this.title = title;
        this.body = body;
        // this.image = image;
        // this.data = data;
    }
}