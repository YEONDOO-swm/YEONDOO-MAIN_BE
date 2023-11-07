package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PaperSimpleIdTitleDTO {
    private String paperId;
    private String title;
    private Boolean isLike;
    public PaperSimpleIdTitleDTO(Paper paper){
        this.paperId = paper.getPaperId();
        this.title = paper.getTitle();
    }
    public PaperSimpleIdTitleDTO(String paperId, String title){
        this.paperId = paperId;
        this.title =title;
    }
    public PaperSimpleIdTitleDTO(TestPython testPython){
        this.paperId = testPython.getPaperId();
        this.title = testPython.getTitle();
    }
    public PaperSimpleIdTitleDTO(String paperId, String title, Boolean isLike){
        this.paperId = paperId;
        this.title =title;
        this.isLike = isLike;
    }
}
