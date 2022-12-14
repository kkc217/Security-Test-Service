package com.capstone.sts.repository;

import com.capstone.sts.domain.UploadFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
