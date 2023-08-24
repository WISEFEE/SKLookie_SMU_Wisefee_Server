package com.sklookiesmu.wisefee.dto.shared.firebase;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@RedisHash(value = "FCMToken", timeToLive = 30)
public class FCMToken {
    @Id
    private String jwtToken;
    @Indexed
    private Long memberPK;
    @Indexed
    private String fireBaseToken;
    @TimeToLive
    private Long ttl;

    @Builder
    public FCMToken(String jwtToken, Long memberPK, String fireBaseToken, Long ttl) {
        this.jwtToken = jwtToken;
        this.memberPK = memberPK;
        this.fireBaseToken = fireBaseToken;
        this.ttl = ttl;
    }
}
