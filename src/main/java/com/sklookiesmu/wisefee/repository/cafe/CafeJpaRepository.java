package com.sklookiesmu.wisefee.repository.cafe;

import com.sklookiesmu.wisefee.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeJpaRepository extends JpaRepository<Cafe, Long>, CafeJpaRepositoryCustom {
}
