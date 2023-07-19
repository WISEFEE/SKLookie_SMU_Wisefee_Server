package com.sklookiesmu.wisefee.repository.cafe;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CafeRepositoryCustom {

    Slice<Cafe> findWithNameFilter(Pageable pageable);
}
