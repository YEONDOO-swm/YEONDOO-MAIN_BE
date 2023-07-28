package com.example.yeondodemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter @Slf4j @Setter @ToString
public class PythonQuestionDTO {
    private String paperid;
    private List<PaperHistory> history;
    private String query;
    public PythonQuestionDTO(String paperid, List<PaperHistory> history, String query){
        //구분이 필요.
        this.paperid = paperid;
        this.history = history;
        this.query = query;
    }
}
