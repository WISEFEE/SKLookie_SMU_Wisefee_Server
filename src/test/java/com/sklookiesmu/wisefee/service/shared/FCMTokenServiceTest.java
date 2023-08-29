package com.sklookiesmu.wisefee.service.shared;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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