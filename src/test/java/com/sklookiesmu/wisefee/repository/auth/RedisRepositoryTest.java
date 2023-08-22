package com.sklookiesmu.wisefee.repository.auth;

import com.sklookiesmu.wisefee.domain.authManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private authRepositoryWithRedis authRepository;

    @AfterEach
    void afterAll() {
        authRepository.deleteAll();
    }

    @Test
    void save() {
        // given
        authManager save = authManager.builder()
                .memberPK("1")
                .jwtToken("jwtTokenExampleValue")
                .fireBaseToken("fireBaseTokenExampleValue")
                .expire_date(LocalDateTime.now())
                .build();

        // when
//        authManager find = authRepository.findById(save.getId()).get();
//        System.out.println("id: " + find.getId());
//        System.out.println("jwtToken : " + find.getJwtToken());
//        System.out.println("fireBaseToken : " + find.getFireBaseToken());
//        System.out.println("expire_date : " + find.getExpire_date());


    }
}