package com.example.yeondodemo.entity;

import com.example.yeondodemo.dto.ScholarDTO;
import com.example.yeondodemo.dto.TestPython;
import com.example.yeondodemo.dto.arxiv.ArxivEntryDTO;
import com.example.yeondodemo.dto.arxiv.ArxivResponseDTO;
import com.example.yeondodemo.dto.arxiv.AuthorDTO;
import com.example.yeondodemo.dto.arxiv.CategoryDTO;
import com.example.yeondodemo.dto.paper.PaperFullMeta;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder @AllArgsConstructor
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
    public Paper(ArxivEntryDTO arxivEntryDTO, String paperId){
        this.title = arxivEntryDTO.getTitle();
        this.abs = arxivEntryDTO.getSummary();
        this.paperId = paperId;
        this.authors = arxivEntryDTO.getAuthors().stream()
                .map(AuthorDTO::getName)
                .collect(Collectors.toList());
        this.categories = arxivEntryDTO.getCategories().stream()
                .map(CategoryDTO::getTerm)
                .collect(Collectors.joining(" "));
        this.url = "https://arxiv.org/pdf/" + this.paperId + ".pdf";

        DateTime dateTime = new DateTime(arxivEntryDTO.getPublished());
        this.year = dateTime.getYear();
    }
    public Paper(String id){
        this.paperId = id;
        this.url = "http://arxiv.org/abs/"+paperId;
    }
    public Paper(TestPython testPython){
        this.paperId = testPython.getPaperId();
        this.title = testPython.getTitle();
        this.authors = testPython.getAuthors();
        this.summary = testPython.getSummary();
        this.url = testPython.getUrl();
        this.year = testPython.getYear();
        this.categories = String.join(" ", testPython.getCategories());
    }
    public Paper(){

    }


}
