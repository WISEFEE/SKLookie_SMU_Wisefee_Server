package com.sklookiesmu.wisefee.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@RedisHash(value = "auth", timeToLive = 30)
public class authManager {
    @Id
    private String id;
    private String memberPK;
    @Indexed
    private String jwtToken;
    private String fireBaseToken;
    private LocalDateTime expire_date;

    @Builder
    public authManager(String memberPK, String jwtToken, String fireBaseToken, LocalDateTime expire_date) {
        this.id = id;
        this.memberPK = memberPK;
        this.jwtToken = jwtToken;
        this.fireBaseToken = fireBaseToken;
        this.expire_date = expire_date;
    }
}
