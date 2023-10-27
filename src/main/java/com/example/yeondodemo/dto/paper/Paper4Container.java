package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter @ToString
public class Paper4Container {
    private String title;
    private String paperId;
    private List<String> authors;
    private int year;
    private String conference;
    private int cites;
    private String url;
    private List<String> subject;
    private Boolean userPdf;
    public Paper4Container(Paper paper){
        this.title = paper.getTitle();
        this.paperId = paper.getPaperId();
        this.authors = paper.getAuthors();
        this.year = paper.getYear();
        this.cites = paper.getCites();
        this.url = paper.getUrl();
        this.conference = paper.getConference();
        this.subject = paper.getCategories() != null
                ? Arrays.asList(paper.getCategories().split(" "))
                : new ArrayList<>();
        this.userPdf = paper.getUserPdf();
    }
}
