package com.sklookiesmu.wisefee.api.v1.shared;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMNotificationRequestDto;
import com.sklookiesmu.wisefee.service.shared.FCMNotificationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "푸시알림 API")
@RestController
@RequiredArgsConstructor
public class FCMNotificationController {

    private final FCMNotificationService fcmNotificationService;


    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestBody FCMNotificationRequestDto requestDto) {
        try {
            fcmNotificationService.sendMessageTo(requestDto);
            return ResponseEntity.ok("알림을 성공적으로 전송했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("알림 보내기를 실패했습니다.");
        }
    }
}





