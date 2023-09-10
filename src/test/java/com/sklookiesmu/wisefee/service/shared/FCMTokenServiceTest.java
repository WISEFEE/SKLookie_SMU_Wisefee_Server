package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.service.shared.interfaces.FCMTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class FCMTokenServiceTest {

    @Autowired
    FCMTokenService fcmTokenService;

    @Test
    public void firstMemberFCM() {
        List<String> tokens = fcmTokenService.getFbTokenByMember(1L);

        //when
        if(tokens != null){
            System.out.println("token : " + tokens.get(0));
        }
        else{
            System.out.println("not found : ");
        }

        tokens = fcmTokenService.getFbTokenByMember(2L);

        //when
        if(tokens != null){
            System.out.println("token : " + tokens.get(0));
        }
        else{
            System.out.println("not found : ");
        }
    }
}