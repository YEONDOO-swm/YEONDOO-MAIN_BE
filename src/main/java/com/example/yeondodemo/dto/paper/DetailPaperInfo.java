package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.PythonPaperInfoDTO;
import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class DetailPaperInfo {
    private String title;
    private int year;
    private String url;
    private String conference;
    private List<String> authors;
    private int cites;
    private String summary;
    private List<String> insights;
    private List<String> questions;
    private List<String> subjectrecommends;
    private List<String> references;
    public DetailPaperInfo(Paper paper, PythonPaperInfoDTO pythonPaperInfoDTO){
        this.title = paper.getTitle();
        this.year = paper.getYear();
        this.url = paper.getUrl();
        this.conference = paper.getConference();
        this.authors = paper.getAuthors();
        this.cites = paper.getCites();
        this.summary = pythonPaperInfoDTO.getSummary();
        this.insights = pythonPaperInfoDTO.getInsights();
        this.questions = pythonPaperInfoDTO.getQuestions();
        this.subjectrecommends = pythonPaperInfoDTO.getSubjectRecommends();
        this.references = pythonPaperInfoDTO.getReferences();
    }
}