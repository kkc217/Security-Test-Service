package com.capstone.sts.repository;

import com.capstone.sts.domain.History;
import com.capstone.sts.domain.QHistory;
import com.capstone.sts.domain.UploadFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.capstone.sts.domain.QHistory.history;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public Long save(History history) {
        em.persist(history);
        return history.getId();
    }

    public History findById(Long historyId) {
        return queryFactory.selectFrom(history)
                .where(history.id.eq(historyId))
                .fetchOne();
    }
}
