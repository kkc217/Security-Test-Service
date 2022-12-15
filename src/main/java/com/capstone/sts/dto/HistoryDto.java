package com.capstone.sts.dto;

import com.capstone.sts.domain.History;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class HistoryDto {

    private String fileName;

    private String result;

    private String createTime;

    public HistoryDto(History history) {
        this.fileName = history.getUploadFile().getName();
        this.result = history.getResult();
        this.createTime = history.getCreateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

}
