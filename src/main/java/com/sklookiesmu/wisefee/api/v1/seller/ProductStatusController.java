package com.sklookiesmu.wisefee.api.v1.seller;

import com.sklookiesmu.wisefee.common.constant.AuthConstant;
import com.sklookiesmu.wisefee.dto.shared.firebase.FCMNotificationRequestDto;
import com.sklookiesmu.wisefee.service.seller.ProductStatusComponent;
import com.sklookiesmu.wisefee.service.shared.FCMNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "SELL-D :: 주문 상태 변경 API")
@RequestMapping("/api/v1/seller/orders")
@RequiredArgsConstructor
public class ProductStatusController {

    private final ProductStatusComponent orderStatusComponent;
    private final FCMNotificationService fcmNotificationService;

    private final String deviceToken = "cauo84oyQ_CAFQU9CcKbtu:APA91bEhakEQbdse7cEJ-43Rbf1ChxtIWxB6iBdzW7SHJuZaFsqQzEDbB-BPy5NVmJoSvyM_Z08ZiejUvi5kwinq_JyVNofk-VIzO5dAYtODrEu6lCTtEvBu83Bi7vQs1U7VjKYO1DaQ";


    @ApiOperation(
            value = "SELL-D-01 :: 주문 승인",
            notes = "매장에 주문이 들어오면 주문 상태를 주문 승인으로 합니다.<br>" +
                    "주문을 승인할 시 고객에게 알림을 전송합니다."
    )
    @PostMapping("/{orderId}/accept")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void acceptOrder(@PathVariable Long orderId) throws IOException {

        orderStatusComponent.acceptOrder(orderId);

        FCMNotificationRequestDto requestDto = new FCMNotificationRequestDto();

        Map<String, String> data = new HashMap<>();
        data.put("title", "주문 승인 완료");
        data.put("body", "주문이 승인되었습니다!");
        requestDto.setData(data);
        requestDto.setTo(deviceToken);
        requestDto.setPriority("high");

        fcmNotificationService.sendMessageTo(requestDto);
    }

    @ApiOperation(
            value = "SELL-D-02 :: 주문 거절",
            notes = "매장에 주문이 들어오면 주문 상태를 주문 거절으로 합니다. <br>" +
                    "주문을 거절할 시 고객에게 알림을 전송합니다."
    )
    @PostMapping("/{orderId}/reject")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void rejectOrder(@PathVariable Long orderId) throws IOException {
        orderStatusComponent.rejectOrder(orderId);

        FCMNotificationRequestDto requestDto = new FCMNotificationRequestDto();

        Map<String, String> data = new HashMap<>();

        data.put("title", "주문 거절");
        data.put("body", "주문이 거절되었습니다.");
        requestDto.setData(data);
        requestDto.setTo(deviceToken);
        requestDto.setPriority("high");

        fcmNotificationService.sendMessageTo(requestDto);
    }

    @ApiOperation(
            value = "SELL-D-03 :: 주문 준비중",
            notes = "주문 상태를 주문 준비중으로 합니다."
    )
    @PostMapping("/{orderId}/prepare")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void preparingOrder(@PathVariable Long orderId) {
        orderStatusComponent.preparingOrder(orderId);
    }

    @ApiOperation(
            value = "SELL-D-04 :: 주문 준비 완료",
            notes = "주문 상태를 주문 준비 완료로 합니다."
    )
    @PostMapping("/{orderId}/complete")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void completeOrder(@PathVariable Long orderId) throws IOException {
        orderStatusComponent.completeOrder(orderId);

        FCMNotificationRequestDto requestDto = new FCMNotificationRequestDto();

        Map<String, String> data = new HashMap<>();

        data.put("title", "주문 준비 완료");
        data.put("body", "주문 준비가 완료되었습니다!");
        requestDto.setData(data);
        requestDto.setTo(deviceToken);
        requestDto.setPriority("high");

        fcmNotificationService.sendMessageTo(requestDto);
    }

    @ApiOperation(
            value = "SELL-D-05 :: 수령 완료",
            notes = "주문 상태를 수령 완료로 합니다."
    )
    @PostMapping("/{orderId}/complete")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void receiveOrder(@PathVariable Long orderId) throws IOException {
        orderStatusComponent.receiveOrder(orderId);

        FCMNotificationRequestDto requestDto = new FCMNotificationRequestDto();

        Map<String, String> data = new HashMap<>();

        data.put("title", "수령 완료");
        data.put("body", "수령이 완료되었습니다!");
        requestDto.setData(data);
        requestDto.setTo(deviceToken);
        requestDto.setPriority("high");

        fcmNotificationService.sendMessageTo(requestDto);
    }

    @ApiOperation(
            value = "SELL-D-06 :: 완료(텀블러 반납)",
            notes = "주문 상태를 완료(텀블러 반납)로 합니다."
    )
    @PostMapping("/{orderId}/complete")
    @PreAuthorize(AuthConstant.AUTH_ROLE_SELLER)
    public void doneOrder(@PathVariable Long orderId) throws IOException {
        orderStatusComponent.doneOrder(orderId);

        FCMNotificationRequestDto requestDto = new FCMNotificationRequestDto();

        Map<String, String> data = new HashMap<>();

        data.put("title", "텀블러 반납 완료");
        data.put("body", "텀블러 반납이 완료되었습니다!");
        requestDto.setData(data);
        requestDto.setTo(deviceToken);
        requestDto.setPriority("high");

        fcmNotificationService.sendMessageTo(requestDto);
    }
}
