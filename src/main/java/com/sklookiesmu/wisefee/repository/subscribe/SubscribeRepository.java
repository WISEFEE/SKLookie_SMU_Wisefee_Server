package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
}
