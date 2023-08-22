package com.sklookiesmu.wisefee.repository.redis;

import com.sklookiesmu.wisefee.domain.FbToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepositoryWithRedis extends CrudRepository<FbToken, String> {
    Optional<List<FbToken>> findAllByfireBaseToken(String fireBaseToken);

    void deleteAllByfireBaseToken(String fireBaseToken);

    Optional<List<FbToken>> findAllBymemberPK(Long memberPK);
}
