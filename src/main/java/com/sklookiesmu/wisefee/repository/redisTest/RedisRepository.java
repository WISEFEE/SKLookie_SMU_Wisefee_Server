package com.sklookiesmu.wisefee.repository.redisTest;

import com.sklookiesmu.wisefee.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional
public class RedisRepository {
    private final PersonRedisRepository redisRepository;

    void test() {
        Person person = new Person("Kang", 21L);
        redisRepository.save(person);
        redisRepository.findById(person.getId());

        redisRepository.count();
//        redisRepository.delete(person);
    }

}
