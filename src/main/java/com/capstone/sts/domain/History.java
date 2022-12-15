package com.capstone.sts.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private UploadFile uploadFile;

    private String result;

    private LocalDateTime createTime;

    @Builder
    public History(UploadFile uploadFile, String result) {
        this.uploadFile = uploadFile;
        this.result = result;
        this.createTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
