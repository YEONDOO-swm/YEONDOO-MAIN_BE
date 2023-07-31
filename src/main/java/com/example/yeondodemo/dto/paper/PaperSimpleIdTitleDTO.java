package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PaperSimpleIdTitleDTO {
    private String paperId;
    private String title;
    public PaperSimpleIdTitleDTO(Paper paper){
        this.paperId = paper.getPaperId();
        this.title = paper.getTitle();
    }
    public PaperSimpleIdTitleDTO(String paperId, String title){
        this.paperId = paperId;
        this.title =title;
    }
}
