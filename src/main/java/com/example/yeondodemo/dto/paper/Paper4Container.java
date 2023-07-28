package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
public class Paper4Container {
    private String title;
    private String paperId;
    private List<String> authors;
    private int year;
    private String conference;
    private int cites;
    private String url;
    public Paper4Container(Paper paper, List<String> authors){
        this.title = paper.getTitle();
        this.paperId = paper.getPaperId();
        this.authors = authors;
        this.year = paper.getYear();
        this.cites = paper.getCites();
        this.url = paper.getUrl();
        this.conference = paper.getConference();
    }
}
