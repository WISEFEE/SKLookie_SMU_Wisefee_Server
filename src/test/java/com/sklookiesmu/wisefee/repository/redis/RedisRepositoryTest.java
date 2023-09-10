package com.sklookiesmu.wisefee.repository.redis;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import com.sklookiesmu.wisefee.service.shared.interfaces.FCMTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private AuthRepositoryWithRedis authRepository;

    @Autowired
    private FCMTokenService fcmTokenService;

//    @AfterEach
//    void afterAll() {
//        authRepository.deleteAll();
//    }

    @Test
    void save() {
        // given
        FCMToken result = FCMToken.builder()
                .jwtToken("jwtTok2enExampleValue")
                .fireBaseToken("fireBaseTokenExampleValue")
                .ttl(Duration.between(LocalDateTime.now(), LocalDateTime.now()).getSeconds())
                .build();

        FCMToken save = authRepository.save(result);


         // when
        FCMToken find = authRepository.findById(save.getJwtToken()).get();
        System.out.println("jwtToken : " + find.getJwtToken());
        System.out.println("fireBaseToken : " + find.getFireBaseToken());
        System.out.println("expire_date : " + find.getTtl());



    }
}