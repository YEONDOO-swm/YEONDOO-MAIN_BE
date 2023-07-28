package com.example.yeondodemo.dto;

import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PaperDTO {
    private String paperId;
    private String title;
    private int likes;
    private boolean islike=false;
    private List<String> authors;
    private int year;
    private String conference;
    private int cites;
    private String url;
    public PaperDTO(PaperWithOutMeta paper){
//        this.paperId = paper.getPaperId();
//        this.title=paper.getTitle();
//        this.likes=paper.getLikes();
//        this.authors=paper.getAuthors();
//        this.year=paper.getYear();
//        this.conference=paper.getConference();
//        this.cites=paper.getCites();
//        this.url=paper.getUrl();
    }
    public PaperDTO(TestPython testPython, PaperWithOutMeta paper){
        this.paperId = paper.getPaperId();
        this.title=testPython.getTitle();
        this.likes=paper.getLikes();
        this.authors=testPython.getAuthors();
        this.year=testPython.getYear();
        this.conference=paper.getConference();
        this.cites=paper.getCites();
        this.url=testPython.getUrl();
    }
}
