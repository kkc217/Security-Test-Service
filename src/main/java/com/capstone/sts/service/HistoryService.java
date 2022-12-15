package com.capstone.sts.service;

import com.capstone.sts.domain.History;
import com.capstone.sts.domain.UploadFile;
import com.capstone.sts.repository.HistoryRepository;
import com.capstone.sts.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public Long saveHistory(History history) {
        return historyRepository.save(history);
    }

    public History getHistory(Long historyId) {
        return historyRepository.findById(historyId);
    }

}
