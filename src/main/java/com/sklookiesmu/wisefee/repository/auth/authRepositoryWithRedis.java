package com.sklookiesmu.wisefee.repository.auth;

import com.sklookiesmu.wisefee.domain.authManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface authRepositoryWithRedis extends JpaRepository<authManager, String> {
    Optional<List<authManager>> findFireBaseTokenByJwtToken(String jwtToken);
}
