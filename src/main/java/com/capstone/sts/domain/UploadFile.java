package com.capstone.sts.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String name;

    private String path;

    private String contentType;

    private Long size;

    @Builder
    public UploadFile(String name, String path, String contentType, Long size) {
        this.name = name;
        this.path = path;
        this.contentType = contentType;
        this.size = size;
    }

}
