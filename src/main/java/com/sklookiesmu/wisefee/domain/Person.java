package com.sklookiesmu.wisefee.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@RedisHash(value = "people", timeToLive = 30)
public class Person {
    @Id
    private String id;
    private String name;
    private Long age;
    private LocalDateTime createdAt;

    public Person(String name, Long age) {
        this.name = name;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }
}
