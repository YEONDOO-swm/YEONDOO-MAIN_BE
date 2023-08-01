package com.example.yeondodemo.dto;

import com.example.yeondodemo.entity.Paper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class TestPython {
    private String paperId;
    private String title;
    private int year;
    private List<String> authors;
    private String summary;
    private String url;
    public TestPython(Paper paper){
        this.paperId = paper.getPaperId();
        this.title = paper.getTitle();
        this.year = paper.getYear();
        this.authors = paper.getAuthors();
        this.summary = paper.getSummary();
        this.url = paper.getUrl();
    }
    public TestPython(){

    }


}
