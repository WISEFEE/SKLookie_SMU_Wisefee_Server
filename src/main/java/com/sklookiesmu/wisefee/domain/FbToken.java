package com.sklookiesmu.wisefee.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RedisHash(value = "refreshToken", timeToLive = 30)
public class FbToken {
    @Id
    private String jwtToken;
    private String memberPK;
    private String fireBaseToken;
    private LocalDate expire_date;

    @Builder
    public FbToken(String memberPK, String jwtToken, String fireBaseToken, LocalDate expire_date) {
        this.jwtToken = jwtToken;
        this.memberPK = memberPK;
        this.fireBaseToken = fireBaseToken;
        this.expire_date = expire_date;
    }
}
