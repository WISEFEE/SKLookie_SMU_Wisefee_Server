package com.sklookiesmu.wisefee.repository.cafe;

import com.sklookiesmu.wisefee.domain.Cafe;
import com.sklookiesmu.wisefee.dto.consumer.CafeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CafeJpaRepositoryCustom {

    /**
     * [매장 리스트 조회]
     * 매장 리스트를 조회하되, 이름순으로 정렬되게끔 한다.
     * @param pageable
     * @return [매장 리스트]
     */
    Slice<Cafe> findWithNameFilter(Pageable pageable);
}
