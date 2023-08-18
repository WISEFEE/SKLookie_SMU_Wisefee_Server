package com.sklookiesmu.wisefee.repository.subscribe;

import com.sklookiesmu.wisefee.domain.Payment;
import com.sklookiesmu.wisefee.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
