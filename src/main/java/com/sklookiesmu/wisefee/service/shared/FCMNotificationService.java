package com.sklookiesmu.wisefee.service.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sklookiesmu.wisefee.dto.shared.firebase.FCMNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/wisefee-b4b12/messages:send";

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/wisefee_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String sendNotificationByToken(FCMNotificationRequestDto requestDto) throws JsonProcessingException {
        if (requestDto.getTo() != null) {
            Message message = Message.builder()
                    .setToken(requestDto.getTo())
                    .setNotification(Notification.builder()
                            .setTitle(requestDto.getData().get("title"))
                            .setBody(requestDto.getData().get("body"))
                            .build())
                    .putAllData(requestDto.getData())
                    .build();

            try {
                firebaseMessaging.send(message);
                return "알림을 성공적으로 전송했습니다. targetUserToken=" + requestDto.getTo();
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                return "알림 보내기를 실패하였습니다. targetUserToken=" + requestDto.getTo();
            }
        } else {
            return "FCM 토큰 값이 존재하지 않습니다.";
        }
    }

    public void sendMessageTo(FCMNotificationRequestDto requestDto) throws IOException {
        String message = sendNotificationByToken(requestDto);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        Response response = client.newCall(request)
                .execute();
        System.out.println(response.body().string());
    }
}