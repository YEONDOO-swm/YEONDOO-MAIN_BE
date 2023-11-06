package com.example.yeondodemo.dto.paper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PaperInfo {
    private int id;
    private String paperid;
    private String infotype;
    private String content;
    public PaperInfo(String paperid, String infotype, String content){
        this.content = content;
        this.paperid = paperid;
        this.infotype = infotype;
    }
}
