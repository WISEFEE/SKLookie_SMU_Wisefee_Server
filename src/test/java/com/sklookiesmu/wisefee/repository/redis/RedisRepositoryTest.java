package com.sklookiesmu.wisefee.repository.redis;

import com.sklookiesmu.wisefee.domain.FbToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private AuthRepositoryWithRedis authRepository;

//    @AfterEach
//    void afterAll() {
//        authRepository.deleteAll();
//    }

    @Test
    void save() {
        // given
        FbToken result = FbToken.builder()
                .jwtToken("jwtTok2enExampleValue")
                .fireBaseToken("fireBaseTokenExampleValue")
                .expire_date(LocalDateTime.now())
                .build();

        FbToken save = authRepository.save(result);


         // when
        FbToken find = authRepository.findById(save.getJwtToken()).get();
        System.out.println("jwtToken : " + find.getJwtToken());
        System.out.println("fireBaseToken : " + find.getFireBaseToken());
        System.out.println("expire_date : " + find.getExpire_date());


    }
}