package com.capstone.sts.repository;

import com.capstone.sts.domain.Family;
import com.capstone.sts.domain.History;
import com.capstone.sts.domain.QFamily;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import static com.capstone.sts.domain.QFamily.family;
import static com.capstone.sts.domain.QHistory.history;

@org.springframework.stereotype.Repository
@RequiredArgsConstructor
public class FamilyRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public Family findByName(String name) {
        return queryFactory.selectFrom(family)
                .where(family.familyName.like(name))
                .fetchOne();
    }

}
