package com.example.yeondodemo.dto.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PaperHistoryDTO {
    private String paperId;
    private String content;
    private String title;
    private Boolean who;
    private String url;
    private Boolean userPdf;
}
