package com.capstone.sts.service;

import com.capstone.sts.domain.UploadFile;
import com.capstone.sts.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;

    @Transactional
    public Long saveUploadFile(UploadFile uploadFile) {
        return uploadFileRepository.save(uploadFile);
    }

}
