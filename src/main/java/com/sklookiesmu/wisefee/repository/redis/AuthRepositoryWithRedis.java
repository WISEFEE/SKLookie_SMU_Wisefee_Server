package com.sklookiesmu.wisefee.repository.redis;

import com.sklookiesmu.wisefee.dto.shared.firebase.FCMToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepositoryWithRedis extends CrudRepository<FCMToken, String> {
    Optional<List<FCMToken>> findAllByfireBaseToken(String fireBaseToken);

    Optional<List<FCMToken>> findAllBymemberPK(Long memberPK);
}
