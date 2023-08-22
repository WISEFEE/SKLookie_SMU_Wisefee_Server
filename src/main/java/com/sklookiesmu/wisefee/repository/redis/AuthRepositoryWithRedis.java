package com.sklookiesmu.wisefee.repository.redis;

import com.sklookiesmu.wisefee.domain.FbToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepositoryWithRedis extends CrudRepository<FbToken, String> {
}
