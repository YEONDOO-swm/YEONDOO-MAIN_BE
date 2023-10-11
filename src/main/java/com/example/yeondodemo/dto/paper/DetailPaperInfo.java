package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.PythonPaperInfoDTO;
import com.example.yeondodemo.dto.paper.item.ItemAnnotation;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class DetailPaperInfo {
    private String title;
    private String paperId;
    private int year;
    private String url;
    private String conference;
    private List<String> authors;
    private int cites;
    private Boolean isLike = false;
    private String welcomeAnswer;
    private String summary;
    private List<String> questions;
    private List<ItemAnnotation> paperItems;
    public DetailPaperInfo(Paper paper, String summary, List<String> questions, List<ItemAnnotation> paperItems, Boolean isLike){
        this.paperId = paper.getPaperId();
        this.title = paper.getTitle();
        this.year = paper.getYear();
        this.url = paper.getUrl();
        this.conference = paper.getConference();
        this.authors = paper.getAuthors();
        this.cites = paper.getCites();
        this.summary = summary;
        this.questions = questions;
        this.paperItems = paperItems;
        this.isLike = isLike;

    }
}