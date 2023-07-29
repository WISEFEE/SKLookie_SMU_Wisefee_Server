package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.SubTicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTicketTypeRepository extends JpaRepository<SubTicketType, Long> {
}
