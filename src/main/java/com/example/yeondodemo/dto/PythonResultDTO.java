package com.example.yeondodemo.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PythonResultDTO {
    @Size(max = 1000)
    private String answer;
    //private List<String> papers;
    private  List<TestPython> papers;
    public PythonResultDTO(){

    }
    public PythonResultDTO(String answer, List<TestPython> papers){
        this.answer = answer;
        this.papers = papers;
    }
}
