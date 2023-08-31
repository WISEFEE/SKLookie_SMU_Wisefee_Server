package com.sklookiesmu.wisefee.repository.cafe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sklookiesmu.wisefee.domain.Cafe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static com.sklookiesmu.wisefee.domain.QCafe.cafe;

public class CafeJpaRepositoryCustomImpl implements CafeJpaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public CafeJpaRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Slice<Cafe> findWithNameFilter(Pageable pageable) {
        List<Cafe> cafeListResponse = jpaQueryFactory
                .selectFrom(cafe)
                .where(cafe.deletedAt.isNull())
                .orderBy(cafe.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)// limit보다 데이터를 1개 더 들고와서 해당 데이터가 있으면 hasNext 변수에 true 넣어서 알림
                .fetch();

        List<Cafe> cafeList = new ArrayList<>();

        for (Cafe cafe1 : cafeListResponse) {
            cafeList.add(cafe1);
        }

        boolean hasNext = false;
        if(cafeList.size()> pageable.getPageSize()){
            cafeList.remove(pageable.getPageSize());
            hasNext=true;
        }
        return new SliceImpl<>(cafeList, pageable, hasNext);
    }
}
