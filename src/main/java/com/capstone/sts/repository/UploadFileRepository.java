package com.capstone.sts.repository;

import com.capstone.sts.domain.UploadFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UploadFileRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public Long save(UploadFile uploadFile) {
        em.persist(uploadFile);
        return uploadFile.getId();
    }

}
