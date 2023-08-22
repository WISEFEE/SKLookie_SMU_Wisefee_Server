package com.sklookiesmu.wisefee.repository.redisTest;

import com.sklookiesmu.wisefee.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, String> {
}
