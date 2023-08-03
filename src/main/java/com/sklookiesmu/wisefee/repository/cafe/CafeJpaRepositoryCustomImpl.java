package com.sklookiesmu.wisefee.repository.cafe;

import com.querydsl.jpa.impl.JPAQuery;
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
        JPAQuery<Long> cafeListResponse = jpaQueryFactory.select(cafe.cafeId)
                .from(cafe)
                .orderBy(cafe.title.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1); // limit보다 데이터를 1개 더 들고와서 해당 데이터가 있으면 hasNext 변수에 true 넣어서 알림

        List<Long> content = new ArrayList<>();
        content = cafeListResponse.fetch();

        boolean hasNext = false;
        if(content.size()> pageable.getPageSize()){
            content.remove(pageable.getPageSize());
            hasNext=true;
        }

        Slice<Long> slice = new SliceImpl<>(content, pageable, hasNext);

        JPAQuery<Cafe> jpaQuery = jpaQueryFactory.selectFrom(cafe)
                .where(cafe.cafeId.in(slice.getContent()));

        return new SliceImpl<>(jpaQuery.fetch(), pageable, hasNext);
    }
}
