package com.example.yeondodemo.dto.paper;

import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.entity.Paper;
import com.example.yeondodemo.entity.PaperWithOutMeta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class PaperDTO {
    private String paperId;
    private String title;
    private int likes;
    private boolean isLike=false;
    private List<String> authors;
    private int year;
    private String conference;
    private int cites;
    private String summary;
    private String url;
    private List<String> subject;
    public void setIsLike(boolean isLike){
        this.isLike = isLike;
    }
    public boolean getIsLike(){
        return this.isLike;
    }
    public PaperDTO(Paper paper){
        this.paperId = paper.getPaperId();
        this.title=paper.getTitle();
        this.likes=paper.getLikes();
        this.authors=paper.getAuthors();
        this.year=paper.getYear();
        this.conference=paper.getConference();
        this.cites=paper.getCites();
        this.url=paper.getUrl();
        this.summary = paper.getSummary();
        this.subject = new ArrayList<>();
        if(paper.getCategories()!=null){
            for(String category: paper.getCategories().split(" ")){
                this.subject.add(category);
            }
        }
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
        this.summary = testPython.getSummary();
    }
    public PaperDTO(TestPython testPython){
        this.paperId = testPython.getPaperId();
        this.title=testPython.getTitle();
        //this.likes=paper.getLikes();
        this.authors=testPython.getAuthors();
        this.year=testPython.getYear();
        //this.conference=paper.getConference();
        //this.cites=paper.getCites();
        this.url=testPython.getUrl();
        this.summary = testPython.getSummary();
    }
}
