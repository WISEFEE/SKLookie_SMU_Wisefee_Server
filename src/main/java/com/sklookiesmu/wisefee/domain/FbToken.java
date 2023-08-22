package com.sklookiesmu.wisefee.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RedisHash(value = "refreshToken", timeToLive = 30)
public class FbToken {
    @Id
    private String jwtToken;
    @Indexed
    private Long memberPK;
    @Indexed
    private String fireBaseToken;
    private LocalDateTime expire_date;

    // 추가: TTL을 위한 필드
    @TimeToLive
    private Long ttl;

    @Builder
    public FbToken(String jwtToken, Long memberPK, String fireBaseToken, LocalDateTime expire_date) {
        this.jwtToken = jwtToken;
        this.memberPK = memberPK;
        this.fireBaseToken = fireBaseToken;
        this.expire_date = expire_date;
        this.ttl = Duration.between(LocalDateTime.now(), expire_date).getSeconds();
    }
}
