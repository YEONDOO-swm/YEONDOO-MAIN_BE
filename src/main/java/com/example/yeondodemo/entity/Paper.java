package com.example.yeondodemo.entity;

import com.example.yeondodemo.dto.ScholarDTO;
import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class Paper extends PaperWithOutMeta{
    private Long paperCode;
    private String paperId;
    private String title;
    private String conference;
    private int cites = 0;
    private String url;
    private String abs = "Default Abastract";
    private String summary = "Default Abastract";
    private List<String> authors = new ArrayList<>();
    private int likes=0;
    private int year;
    private LocalDate lastUpdate;
    private String comments;
    private String journalRef;
    private String doi;
    private String reportNo;
    private String categories;
    private String license;
    private String version;
    public void setScholar(ScholarDTO scholarDTO){
        this.conference = scholarDTO.getConference();
        this.cites = scholarDTO.getCites();
    }
    public void setMeta(TestPython testPython){
        this.year = testPython.getYear();
        this.title = testPython.getTitle();
        this.url = testPython.getUrl();
        this.authors = testPython.getAuthors();
    }
    public Paper(String id){
        this.paperId = id;
        this.url = "http://arxiv.org/abs/"+paperId;
    }
    public Paper(){

    }


}
