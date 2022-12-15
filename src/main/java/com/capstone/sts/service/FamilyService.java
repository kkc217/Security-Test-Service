package com.capstone.sts.service;

import com.capstone.sts.domain.Family;
import com.capstone.sts.domain.History;
import com.capstone.sts.repository.FamilyRepository;
import com.capstone.sts.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FamilyService {

    private final FamilyRepository familyRepository;


    public String getFamilyInfo(String name) {
        Family family = familyRepository.findByName(name);

        return family == null ? "해당 패밀리의 설명이 없습니다..." : family.getInformation();
    }

}
