package com.example.yeondodemo.dto;

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


}
